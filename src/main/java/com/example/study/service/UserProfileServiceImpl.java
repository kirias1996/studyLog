package com.example.study.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.study.dto.UserProfileDto;
import com.example.study.entity.User;
import com.example.study.repository.UserRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Value("${app.default-icon-path}")
	String defaultIconUrl;

	private final UserRepository userRepository;

	public UserProfileServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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

		return dto;
	}

	/*
	 * Controllerクラスでuserのnullチェック,dtoのバリデーションチェック済みのため
	 * Serviceクラスではチェック処理は実施しない
	 * */
	@Override
	@Transactional
	public void updateUserProfile(User user, UserProfileDto dto) {
		String uploadFilePath = null;
		String baseFilePath = System.getProperty("user.dir");
		try {
			if (dto.getIconImage() != null && !dto.getIconImage().isEmpty()) {
				uploadFilePath = saveIconImage(dto.getIconImage());
				user.setIconUrl(uploadFilePath);

			}
			user.setUserName(dto.getUserName());
			user.setProfileText(dto.getProfileText());
			userRepository.save(user);
		} catch (Exception e) {
			//DB更新失敗時に保存済みアップロードファイルを削除
			if (uploadFilePath != null) {
				deleteFile(baseFilePath + uploadFilePath);
			}

			throw new RuntimeException("プロフィール更新に失敗しました。");
		}
	}

	/*
	 * アイコン画像の保存に失敗したときにはユーザの想定しないアイコン画像が表示される可能性があるため
	 * エラー画面に遷移させる。
	 * */
	private String saveIconImage(MultipartFile file) throws IOException {
		String uploadDir = System.getProperty("user.dir") + "/upload/";
		String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
		Path filePath = Paths.get(uploadDir, fileName);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return "/upload/" + fileName;
	}

	private void deleteFile(String filePath) {
		try {
			Path path = Paths.get(filePath);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			System.err.println("ファイル削除に失敗しました。:" + filePath);
		}
	}
}
