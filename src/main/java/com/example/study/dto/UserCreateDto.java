package com.example.study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDto {
	@NotBlank(message="{validation.email.blank}")
	@Email(message="{validation.email.invalid}")
	@Size(max=255,message="{validation.email.sizeover}")
	private String email;
	
	/*パスワードのハッシュ化にはBcryptアルゴリズムを採用
	 * 72文字目以降は無視されてしまうため、72文字までの入力としている
	 * */
	@NotBlank(message="{validation.password.blank}")
	@Size(min=8,max=72,message="{validation.password.size}")
	private String password;
	
	@NotBlank(message="{validation.password.blank}")
	@Size(min=8,max=72,message="{validation.password.size}")
	private String confirmPassword;
	
	public UserCreateDto() {
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	

}
