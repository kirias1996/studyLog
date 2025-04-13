
package com.example.study.service;

import java.util.List;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;

public interface ReportService {

	List<Report> getReportsByUserId(int userId);
	
	Report getReportById(int id);
	
	void createReport(ReportRequestDto dto);
	
	ReportRequestDto toReportRequestDto(Report report);
	
	void updateReport(ReportRequestDto dto);
}
