package com.example.study.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/*アプリケーション全体で共通の例外処理を定義するクラス*/
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ReportNotFoundException.class)
	public String handleReportNotFound(ReportNotFoundException ex,Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/report-not-found";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDenied(AccessDeniedException ex,Model model) {
		model.addAttribute("errorMessage",ex.getMessage());
		return "error/report-access-deny";
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "error/exception";
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex,Model model) {
		model.addAttribute("errorMessage", "アップロードできるファイルサイズは10MBまでです。");
		return "error/exception";
	}
	
}
