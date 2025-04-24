package com.example.study.security;

import org.springframework.stereotype.Component;

import com.example.study.entity.User;
import com.example.study.repository.UserRepository;

@Component
public class LoginUserProvider {

	private final UserRepository userRepository;

	public LoginUserProvider(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getLoginUser(CustomUserDetails userDetails) {
		User user = userRepository.findByAuth(userDetails.getAuth())
				.orElseThrow(() -> new RuntimeException("ログインユーザが見つかりませんでした。"));
		
		return user;
	}
}
