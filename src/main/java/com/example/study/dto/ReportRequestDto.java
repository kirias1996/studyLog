package com.example.study.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class ReportRequestDto {

	private int id;

	private int userId;
	
	@NotBlank(message="{validation.title.blank}")
	@Size(max = 255, message = "{validation.title.sizeover}")
	private String title;

	@NotBlank(message="{validation.content.blank}")
	@Size(max = 255, message = "{validation.content.sizeover}")
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="{validation.learningDate.blank}")
	private LocalDate learningDate;

	@Min(value = 0,message="{validation.learningHours.invalid}")
	@Max(value = 23,message="{validation.learningHours.invalid}")
	private int learningHours;

	private int learningMinutes;

	private double displayLearningTimes;

	private int tagId;

	@NotBlank(message = "{validation.tagName.blank}")
	@Size(max = 20, message = "{validation.tagName.sizeover}")
	private String tagName;

	public ReportRequestDto() {

	}

	/*カスタムバリデーション*/
	@AssertTrue(message = "{validation.learningMinutes.invalid}")
	public boolean isValidMinutes() {
		return List.of(0, 15, 30, 45).contains(learningMinutes);
	}

	public String capitalizeDisplayTagName() {
		if (this.tagName == null || this.tagName.isEmpty()) {
			return "";
		}

		return this.tagName.substring(0, 1).toUpperCase() + this.tagName.substring(1);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public double getDisplayLearningTimes() {
		return displayLearningTimes;
	}

	public void setDisplayLearningTimes(double displayLearningTimes) {
		this.displayLearningTimes = displayLearningTimes;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}