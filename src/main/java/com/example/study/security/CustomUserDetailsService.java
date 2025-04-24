package com.example.study.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.study.entity.Auth;
import com.example.study.repository.AuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final AuthRepository authRepository;

	public CustomUserDetailsService(AuthRepository authRepository) {
		this.authRepository = authRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Auth auth = authRepository.findByEmail(email)
			.orElseThrow(()-> new UsernameNotFoundException("ご入力されたメールアドレスは未登録です。"));

		return new CustomUserDetails(auth);
	}

}
