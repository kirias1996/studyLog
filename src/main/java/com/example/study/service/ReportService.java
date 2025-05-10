
package com.example.study.service;

import java.util.List;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;

public interface ReportService {

	List<Report> getReportsByUserId(int userId);

	Report getReportById(int id);

	public Report getUserOwnedReport(int reportId, int userId);

	void createReport(ReportRequestDto dto);

	ReportRequestDto toReportRequestDto(Report report);

	void updateReport(ReportRequestDto dto, int userId);

	void deleteReport(int reportId,int userId);

	boolean existById(int id);
	
	double getTotalLearningTimes(int userId);
	
	int getTotalLearningDays(int userId);
	
	int getConsecutiveLearningDays(int userId);
}
