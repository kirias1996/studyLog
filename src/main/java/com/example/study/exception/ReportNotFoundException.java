package com.example.study.exception;

public class ReportNotFoundException extends RuntimeException{
	public ReportNotFoundException(String message) {
		super(message);
	}
}
