package com.example.study.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.study.dto.ReportRequestDto;

@Component
public class ReportLearningTimesValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		/*
		 * ReportRequestDtoクラスとかサブクラスのみバリデーション対象とする
		 * */
		return ReportRequestDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ReportRequestDto dto = (ReportRequestDto) target;
		validateLearningTimePositive(dto, errors);

	}

	private void validateLearningTimePositive(ReportRequestDto dto, Errors errors) {
		if (dto.getLearningHours() == 0 && dto.getLearningMinutes() == 0) {
			errors.reject("validation.learningTimes.positive");
		}

	}

}
