package com.example.study.security;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.example.study.entity.User;
import com.example.study.repository.UserRepository;
import com.example.study.util.message.MessageUtil;

@Component
public class LoginUserProvider {

	private final UserRepository userRepository;
	private final MessageUtil messageUtil;

	public LoginUserProvider(UserRepository userRepository,MessageUtil messageUtil) {
		this.userRepository = userRepository;
		this.messageUtil = messageUtil;
	}

	public User getLoginUser(CustomUserDetails userDetails) {
		if (userDetails == null) {
			throw new RuntimeException(messageUtil.getMessage("authUser.NotFound", null, LocaleContextHolder.getLocale()));
		}
		

		User user = userRepository.findByAuth(userDetails.getAuth())
				.orElseThrow(() -> new RuntimeException(messageUtil.getMessage("loginUser.NotFound", null, LocaleContextHolder.getLocale())));

		return user;
	}
}
