package com.example.study.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study.dto.UserCreateDto;
import com.example.study.entity.Auth;
import com.example.study.entity.User;
import com.example.study.repository.AuthRepository;
import com.example.study.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, AuthRepository authRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authRepository = authRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createUser(UserCreateDto dto,String url) {
		Auth auth = new Auth();
		auth.setEmail(dto.getEmail());
		auth.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
		authRepository.save(auth);

		User user = new User();
		user.setAuth(auth);
		userRepository.save(user);
		
		if (user.getIconUrl() == null) {
			user.setIconUrl(url);
		}
		
	}

	public boolean isPasswordMatch(UserCreateDto dto) {
		return dto.getPassword().equals(dto.getConfirmPassword());
	}

	public boolean isDuplicatedEmail(UserCreateDto dto) {
		return authRepository.existsByEmail(dto.getEmail());
	}

}
