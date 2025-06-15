package com.example.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/signup", "/css/*", "/js/*").permitAll()
					.anyRequest().authenticated())
				.formLogin(login -> login
					.loginProcessingUrl("/auth/login")
					.loginPage("/login")
					.usernameParameter("email")
					.defaultSuccessUrl("/dashboard",true)
					.failureUrl("/login?error")
					.permitAll())
				.logout(logout -> logout
					.logoutUrl("/auth/logout")
					.logoutSuccessUrl("/logout-success")
					.permitAll()
					);

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
