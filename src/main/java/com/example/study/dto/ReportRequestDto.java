package com.example.study.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

public class ReportRequestDto {

  private int id;
	
	private String title;

	private String content;
	

	@DateTimeFormat(pattern = "yyyy-MM-dd")

	private LocalDate learningDate;
	
	@Min(0)
	@Max(23)
	private int learningHours;
	
	private int learningMinutes;
	
	public ReportRequestDto() {
		
	}
	
	/*カスタムバリデーション*/
	@AssertTrue(message = "分は0,15,30,45のいずれかを選択してください。")
	public boolean isValidMinutes() {
		return List.of(0,15,30,45).contains(learningMinutes);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getLearningDate() {
		return learningDate;
	}

	public void setLearningDate(LocalDate learningDate) {
		this.learningDate = learningDate;
	}

	public int getLearningHours() {
		return learningHours;
	}

	public void setLearningHours(int learningHours) {
		this.learningHours = learningHours;
	}

	public int getLearningMinutes() {
		return learningMinutes;
	}

	public void setLearningMinutes(int learningMinutes) {
		this.learningMinutes = learningMinutes;
	}


}