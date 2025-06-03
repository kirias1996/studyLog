package com.example.study.validation;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.example.study.util.message.MessageUtil;

@Component
public class ReportTagValidator{
	
	private final MessageUtil messageUtil;
	
	public ReportTagValidator(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}
	
	public void validateTagName(String tagName) {
		
		//null or 空白だけの文字列は禁止
		if (tagName == null || tagName.trim().isEmpty()) {
			throw new  IllegalArgumentException(messageUtil.getMessage("validation.tagName.blank", null, LocaleContextHolder.getLocale())); 
		}
		
		if(tagName.length()> 20) {
			throw new  IllegalArgumentException(messageUtil.getMessage("input.tagName.sizeover", null, LocaleContextHolder.getLocale()));
		}
	}
	
}
