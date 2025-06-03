package com.example.study.controller.signup;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.study.dto.UserCreateDto;
import com.example.study.service.UserService;
import com.example.study.util.message.MessageUtil;

@Controller
public class SignupController {
	
	@Value("${app.default-icon-path}")
	String defaultIconUrl;

	private final UserService userService;
	private final MessageUtil messageUtil;

	public SignupController(UserService userService,MessageUtil messageUtil) {
		this.userService = userService;
		this.messageUtil = messageUtil;
	}

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("userCreateDto", new UserCreateDto());
		return "signup";
	}

	@PostMapping("/signup")
	public String processSignup(@Valid @ModelAttribute("userCreateDto") UserCreateDto dto, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "signup";
		}

		//パスワード入力が一致しているかを確認
		if (!userService.isPasswordMatch(dto)) {
			model.addAttribute("errorMessage", messageUtil.getMessage("login.password.cofirm.invalid.error",null,LocaleContextHolder.getLocale()));
			return "signup";
		}

		//入力したemailアドレスが登録済みか確認(authテーブルのemailアドレスで検索をかける必要がある)
		if (userService.isDuplicatedEmail(dto)) {
			model.addAttribute("errorMessage", messageUtil.getMessage("login.duplicated.mailAddress.error",null,LocaleContextHolder.getLocale()));
			return "signup";
		}

		userService.createUser(dto,defaultIconUrl);
		redirectAttributes.addFlashAttribute("successMessage", messageUtil.getMessage("createUser.success",null,LocaleContextHolder.getLocale()));

		return "redirect:/login";
	}
}
