package com.example.study.service;

import com.example.study.dto.UserProfileDto;
import com.example.study.entity.User;

public interface UserProfileService {
	UserProfileDto toUserProfileDto(User user,String email);
}
