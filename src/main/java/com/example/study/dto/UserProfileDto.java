package com.example.study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileDto {
	/*emailは表示用でユーザからの変更を受け付けない仕様のため
	バリデーションチェックをしない*/
	private String email;
	
	@NotBlank
	@Size(max = 50,message="ユーザ名は{max}文字以内で入力してください。")
	private String userName;
	
	@Size(max = 255,message="プロフィール文は{max}文字以内で入力してください。")
	private String profileText;
	
	private String iconUrl;
	
	private MultipartFile iconImage;
	
	private boolean defaultIcon;
	
	public UserProfileDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileText() {
		return profileText;
	}

	public void setProfileText(String profileText) {
		this.profileText = profileText;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public MultipartFile getIconImage() {
		return iconImage;
	}

	public void setIconImage(MultipartFile iconImage) {
		this.iconImage = iconImage;
	}

	public boolean isDefaultIcon() {
		return defaultIcon;
	}

	public void setDefaultIcon(boolean defaultIcon) {
		this.defaultIcon = defaultIcon;
	}
	
}
