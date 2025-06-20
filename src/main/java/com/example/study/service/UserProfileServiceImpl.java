package com.example.study.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.study.dto.UserProfileDto;
import com.example.study.entity.User;
import com.example.study.repository.UserRepository;
import com.example.study.util.CloudinaryParamsHelper;
import com.example.study.util.InputValidator;
import com.example.study.util.message.MessageUtil;

@Service
public class UserProfileServiceImpl implements UserProfileService, ImageService {

	@Value("${app.default-icon-path}")
	String defaultIconUrl;
	
	@Value("${cloudinary.upload.folder}")
	String uploadFolder;

	private final UserRepository userRepository;
	private final Cloudinary cloudinary;
	private final MessageUtil messageUtil;

	public UserProfileServiceImpl(UserRepository userRepository, Cloudinary cloudinary,MessageUtil messageUtil) {
		this.userRepository = userRepository;
		this.cloudinary = cloudinary;
		this.messageUtil = messageUtil;
	}

	@Override
	public UserProfileDto toUserProfileDto(User user, String email) {
		UserProfileDto dto = new UserProfileDto();
		dto.setEmail(email);
		dto.setUserName(user.getUserName());
		dto.setProfileText(user.getProfileText());
		if (user.getIconUrl() == null) {
			dto.setIconUrl(defaultIconUrl);
			dto.setDefaultIcon(false);
		} else {
			dto.setIconUrl(user.getIconUrl());
			dto.setDefaultIcon(user.getIconUrl().equals(defaultIconUrl));
		}
		dto.setIconPublicId(user.getIconPublicId());
		dto.setIconUrl(user.getIconUrl());

		return dto;
	}

	/*
	 * Controllerクラスでuserのnullチェック,dtoのバリデーションチェック済みのため
	 * Serviceクラスではチェック処理は実施しない
	 * 新規画像がアップロードされなかった場合は既存画像(iconPublicId)を保持する
	 * デフォルトアイコン使用時はiconPublicIdがnullであることを想定
	 * */
	@Override
	@Transactional
	public void updateUserProfile(User user, UserProfileDto dto) {
		Map<String, Object> result = null;
		String newPublicId = null;
		String oldPublicId = user.getIconPublicId();
		try {
			if (InputValidator.isNotEmpty(dto.getIconImage())) {
				newPublicId = "user_" + user.getId() + "_" + UUID.randomUUID();
				Map<String, Object> optionParameters = CloudinaryParamsHelper.defaultParams(newPublicId,uploadFolder);
				result = upload(dto.getIconImage(), optionParameters);
				user.setIconUrl(result.get("secure_url").toString());
				newPublicId = result.get("public_id").toString();
				user.setIconPublicId(newPublicId);
			}
			user.setUserName(dto.getUserName());
			user.setProfileText(dto.getProfileText());
			userRepository.save(user);

			if (shouldDeleteOldIcon(dto,oldPublicId)) {
				deleteFile(oldPublicId);
			}

		} catch (Exception e) {

			//DB更新失敗時に保存済みアップロードファイルを削除
			if (InputValidator.isNotBlank(newPublicId)) {
				deleteFile(newPublicId);
			}
			throw new RuntimeException(messageUtil.getMessage("updateUserProfile.error", null, LocaleContextHolder.getLocale()));
		}
	}

	private boolean shouldDeleteOldIcon(UserProfileDto dto,String oldPublicId) {
		return InputValidator.isNotBlank(oldPublicId)
				&& !dto.isDefaultIcon()
				&& InputValidator.isNotEmpty(dto.getIconImage());
	}

	/*
	 * アイコン画像の保存に失敗したときにはユーザの想定しないアイコン画像が表示される可能性があるため
	 * エラー画面に遷移させる。
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> upload(MultipartFile file, Map<String, Object> param) throws IOException {
		return cloudinary.uploader().upload(file.getBytes(), param);
	}

	@Override
	public void deleteFile(String publicId) {
		try {
			/*
			 * destoryに第2引数でObjectUtils.emptyMap()を指定している理由
			 * destoryメソッドはオーバーロードされておらず2つの引数指定が必須
			 * ObjectUtils.emptyMap()を指定することで空のMapが作られる。
			 * 追加オプションなしであることを明示するために渡している。
			 * */
			cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
		} catch (IOException e) {
			System.err.println(messageUtil.getMessage("deleteIconImagefile.error", null, LocaleContextHolder.getLocale()));
		}
	}
}
