package com.example.study.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.study.entity.Auth;

public class CustomUserDetails implements UserDetails {

	private final Auth auth;

	public CustomUserDetails(Auth auth) {
		this.auth = auth;
	}

	public Auth getAuth() {
		return auth;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*SpringSecurityでは"ROLE_"が付与されていないと想定動作しないため
		"ROLE_"を先頭に付与*/
		return List.of(new SimpleGrantedAuthority("ROLE_" + auth.getRole()));
	}

	@Override
	public String getPassword() {
		return auth.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return auth.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
