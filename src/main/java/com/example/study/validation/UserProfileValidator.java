package com.example.study.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.example.study.dto.UserProfileDto;

@Component
public class UserProfileValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		/*
		 * UserProfileDtoクラスと同じかサブクラスならtrueを返す
		 * isAssinableFromを使うことで他のDtoでバリエーションが使われることを防ぐ
		 * */
		return UserProfileDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserProfileDto dto = (UserProfileDto) target;
		MultipartFile file = dto.getIconImage();

		if (file == null || file.isEmpty()) {
			return;
		}
		//ファイル拡張子チェック
		validateFileExtension(file, errors);
		//ファイルサイズチェック
		validateMaxFileSize(file, errors);
	}

	private void validateFileExtension(MultipartFile file, Errors errors) {
		/*(?i)で大文字/小文字を区別しない正規表現マッチングを適用*/
		if (file.getOriginalFilename() != null && !file.isEmpty()
				&& !file.getOriginalFilename().matches("(?i)^.+\\.(png|jpg|jpeg)$")) {
			errors.rejectValue("iconImage", "validation.fileExtension.invalid");
		}
	}

	private void validateMaxFileSize(MultipartFile file, Errors errors) {
		// サイズチェック（2MB = 2 * 1024 * 1024）
		if (file.getSize() > 2 * 1024 * 1024) {
			errors.rejectValue("iconImage", "validation.file.max.size.over");
		}
	}

}
