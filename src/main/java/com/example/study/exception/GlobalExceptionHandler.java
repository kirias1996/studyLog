package com.example.study.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*アプリケーション全体で共通の例外処理を定義するクラス*/
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ReportNotFoundException.class)
	public String handleReportNotFound(ReportNotFoundException ex,Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/report-not-found";
	}
	
}
