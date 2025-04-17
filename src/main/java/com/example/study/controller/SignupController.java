package com.example.study.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.study.dto.UserCreateDto;
import com.example.study.service.UserService;

@Controller
public class SignupController {
	
	private final UserService userService;
	
	public SignupController(UserService userService) {
		this.userService = userService;
	}

	
	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("userCreateDto", new UserCreateDto());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String processSignup(@Valid  @ModelAttribute("userCreateDto") UserCreateDto dto,BindingResult result ,Model model,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return "signup";
		}
		
		//パスワード入力が一致しているかを確認
		if (!userService.isPasswordMatch(dto)) {
			model.addAttribute("errorMessage", "パスワードが一致しません。");
			return "signup";			
		}
		
		//入力したemailアドレスが登録済みか確認(authテーブルのemailアドレスで検索をかける必要がある)
		if(userService.isDuplicatedEmail(dto)) {
			model.addAttribute("errorMessage", "入力されたメールアドレスは登録済みです。");
			return "signup";
		}
		
		userService.createUser(dto);
		redirectAttributes.addFlashAttribute("successMessage", "ユーザ登録が完了しました。");
		
		return "redirect:signup";
	}
}
