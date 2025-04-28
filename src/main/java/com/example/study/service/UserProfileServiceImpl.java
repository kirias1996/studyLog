package com.example.study.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.study.dto.UserProfileDto;
import com.example.study.entity.User;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Value("${app.default-icon-path}")
	String defaultIconUrl;
	
	@Override
	public UserProfileDto toUserProfileDto(User user,String email) {
		UserProfileDto dto = new UserProfileDto();
		
		dto.setEmail(email);
		dto.setUserName(user.getUserName());
		dto.setProfileText(user.getProfileText());
		dto.setIconUrl(user.getIconUrl());
		dto.setDefaultIcon(user.getIconUrl().equals(defaultIconUrl));
				
		return dto;
	}
}
