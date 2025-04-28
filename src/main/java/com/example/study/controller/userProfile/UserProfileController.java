package com.example.study.controller.userProfile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.study.entity.User;
import com.example.study.security.CustomUserDetails;
import com.example.study.security.LoginUserProvider;
import com.example.study.service.UserProfileService;

@Controller
public class UserProfileController {

	@Value("${app.default-icon-path}")
	String defaultIconUrl;

	private final UserProfileService userProfileService;
	private final LoginUserProvider loginUserProvider;

	public UserProfileController(UserProfileService userProfileService, LoginUserProvider loginUserProvider) {
		this.userProfileService = userProfileService;
		this.loginUserProvider = loginUserProvider;
	}

	@GetMapping("/userProfile")
	public String showUserProfileForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		//ログインユーザの情報を取得してServiceクラスに渡す
		User user = loginUserProvider.getLoginUser(userDetails);

		/*
		 * SpringSecurityのUserDetails(認証情報を管理するクラス)のメソッドがgetUsernameとなっている
		 * そのクラスを実装し、OverrideしているメソッドのためgetUsernameといったメソッド名を指定
		 * 中身としてはメールアドレスを取得している
		 * */
		model.addAttribute("userProfile", userProfileService.toUserProfileDto(user, userDetails.getUsername()));
		return "userProfile/userProfileView";
	}
}
