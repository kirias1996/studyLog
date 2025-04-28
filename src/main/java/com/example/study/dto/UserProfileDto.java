package com.example.study.dto;

public class UserProfileDto {
	
	private String email;
	
	private String userName;
	
	private String profileText;
	
	private String iconUrl;
	
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

	public boolean isDefaultIcon() {
		return defaultIcon;
	}

	public void setDefaultIcon(boolean defaultIcon) {
		this.defaultIcon = defaultIcon;
	}
	
}
