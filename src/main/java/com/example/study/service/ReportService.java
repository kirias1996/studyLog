package com.example.study.service;

import java.util.List;

import com.example.study.entity.Report;

public interface ReportService {

	List<Report> getReportsByUserId(int userId);

}
