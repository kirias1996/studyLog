package com.example.study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginFormDto {

	@NotBlank(message = "{validation.email.blank}")
	@Email(message = "{validation.email.invalid}")
	private String email;
	
	@NotBlank(message = "{validation.password.blank}")
	private String password;

	public LoginFormDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
