package com.example.study.util;

import org.springframework.web.multipart.MultipartFile;

public class InputValidator {

	private InputValidator() {

	}

	public static boolean isNotBlank(String str) {
		return str != null && !str.isBlank();
	}

	public static boolean isBlank(String str) {
		return str == null || str.isBlank();
	}

	/*
	 * 空文字のみtrue(" "や"\n"を含むとfalse)
	 * */
	public static boolean isEmptyOnly(String str) {
		return str != null && str.isEmpty();
	}

	public static boolean isNotEmpty(MultipartFile file) {
		return file != null && !file.isEmpty();
	}

	public static boolean isEmpty(MultipartFile file) {
		return file == null || file.isEmpty();
	}
}
