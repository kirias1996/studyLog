package com.example.study.validation;

public class ReportTagValidator{
	public static void validateTagName(String tagName) {
		
		//null or 空白だけの文字列は禁止
		if (tagName == null || tagName.trim().isEmpty()) {
			throw new  IllegalArgumentException("タグ名を入力してください。"); 
		}
		
		if(tagName.length()> 20) {
			throw new  IllegalArgumentException("タグ名は20文字以内で入力してください。");
		}
	}
	
}
