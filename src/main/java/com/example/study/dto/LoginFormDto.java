package com.example.study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginFormDto {

	@NotBlank(message = "{validation.email.blank}")
	@Email(message = "{validation.email.invalid}")
	private String email;
	
	@NotBlank(message = "{validation.password.blank}")
	@Size(min=8,max=72,message="{validation.password.size}")
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
