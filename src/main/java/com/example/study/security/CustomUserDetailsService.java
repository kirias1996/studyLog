package com.example.study.security;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.study.entity.Auth;
import com.example.study.repository.AuthRepository;
import com.example.study.util.message.MessageUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final AuthRepository authRepository;
	private final MessageUtil messageUtil;

	public CustomUserDetailsService(AuthRepository authRepository,MessageUtil messageUtil) {
		this.authRepository = authRepository;
		this.messageUtil = messageUtil;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(()-> new UsernameNotFoundException(messageUtil.getMessage("unregistered.mailAddress", null, LocaleContextHolder.getLocale())));
		return new CustomUserDetails(auth);
	}

}
