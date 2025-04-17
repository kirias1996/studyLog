package com.example.study.service;

import com.example.study.dto.UserCreateDto;

public interface UserService {
	void createUser(UserCreateDto dto);

	boolean isPasswordMatch(UserCreateDto dto);

	boolean isDuplicatedEmail(UserCreateDto dto);
}
