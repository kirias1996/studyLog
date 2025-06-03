package com.example.study.controller.login;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.study.dto.LoginFormDto;
import com.example.study.security.CustomUserDetails;
import com.example.study.util.message.MessageUtil;

@Controller
public class LoginController {

	private final MessageUtil messageUtil;

	public LoginController(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}

	/*
	 * ログイン成功後のリダイレクト処理はSecurityConfigクラスのfilterChainメソッドに記述した
	 * 内容でControllerクラスへの記述は不要
	*/

	@GetMapping("/login")
	public String showLoginForm(@RequestParam(value = "error", required = false) String error,
			Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("loginFormDto", new LoginFormDto());
		if (error != null) {
			model.addAttribute("errorMessage", messageUtil.getMessage("login.invalid.error",null,LocaleContextHolder.getLocale()));
		}

		// ユーザがログイン済みであればトップ画面に未ログインであればログイン画面に遷移		
		if (userDetails != null) {
			return "redirect:/reports";
		}

		return "auth/login";
	}

	@GetMapping("/logout-success")
	public String logoutSuccess(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("successMessage", messageUtil.getMessage("logout.success",null,LocaleContextHolder.getLocale()));
		return "redirect:/login";
	}

	@PostMapping("/login")
	public String authLogin(@Valid @ModelAttribute("loginFormDto") LoginFormDto dto, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (result.hasErrors()) {
			return "auth/login";
		}

		//Spring Securityに認証処理を委ねる(/auth/loginにforward)
		request.getRequestDispatcher("/auth/login").forward(request, response);
		return null;
	}

}
