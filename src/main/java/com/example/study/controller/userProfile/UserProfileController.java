package com.example.study.controller.userProfile;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.study.dto.UserProfileDto;
import com.example.study.entity.User;
import com.example.study.security.CustomUserDetails;
import com.example.study.security.LoginUserProvider;
import com.example.study.service.UserProfileService;
import com.example.study.util.message.MessageUtil;
import com.example.study.validation.UserProfileValidator;

@Controller
public class UserProfileController {

	@Value("${app.default-icon-path}")
	String defaultIconUrl;

	private final UserProfileService userProfileService;
	private final LoginUserProvider loginUserProvider;
	private final UserProfileValidator userProfileValidator;
	private final MessageUtil messageUtil;

	public UserProfileController(UserProfileService userProfileService, LoginUserProvider loginUserProvider,
			UserProfileValidator userProfileValidator, MessageUtil messageUtil) {
		this.userProfileService = userProfileService;
		this.loginUserProvider = loginUserProvider;
		this.userProfileValidator = userProfileValidator;
		this.messageUtil = messageUtil;
	}

	@GetMapping("/userProfile")
	public String showUserProfile(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
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

	@GetMapping("/userProfile/edit")
	public String showUserProfileForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		//ログインユーザの情報を取得してServiceクラスに渡す
		User user = loginUserProvider.getLoginUser(userDetails);

		/*
		 * SpringSecurityのUserDetails(認証情報を管理するクラス)のメソッドがgetUsernameとなっている
		 * そのクラスを実装し、OverrideしているメソッドのためgetUsernameといったメソッド名を指定
		 * 中身としてはメールアドレスを取得している
		 * */
		model.addAttribute("userProfile", userProfileService.toUserProfileDto(user, userDetails.getUsername()));
		return "userProfile/userProfileEdit";
	}

	@InitBinder("userProfile")
	protected void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(userProfileValidator);
	}

	/*DB更新なのにPUTではなくPOSTを採用している理由
	 * HTMLの仕様でmethod=GET,POSTメソッドのみサポートされている
	 * Springの挙動としてmultipart/form-data + PUT は MultipartResolver や 
	 * BindingResult が正しく機能しないことがある
	 * 上記の理由からバリデーションやバインドが正常に動作させるためにPOSTを採用している
	*/
	@PostMapping("/userProfile")
	public String editUserProfile(@ModelAttribute("userProfile") @Valid UserProfileDto dto,
			BindingResult result, @AuthenticationPrincipal CustomUserDetails userDetails,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "userProfile/userProfileEdit";
		}

		//ログインユーザの情報を取得してDB更新条件に利用
		User user = loginUserProvider.getLoginUser(userDetails);
		userProfileService.updateUserProfile(user, dto);
		redirectAttributes.addFlashAttribute("successMessage",
				messageUtil.getMessage("createUser.success", null, LocaleContextHolder.getLocale()));

		return "redirect:/userProfile";
	}
}
