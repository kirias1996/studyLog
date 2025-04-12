package com.example.study.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study.entity.Report;
import com.example.study.exception.ReportNotFoundException;
import com.example.study.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

	
	/*コンストラクタインジェクションを採用した理由
	SpringFrameWorkの公式ドキュメントで推奨されているため
	メリット
	①finalが使え、不変性を保つことができる
	②依存関係が明確でコードが読みやすくなる
	③テスト時にモック注入がしやすい*/
	private final ReportRepository reportRepository;
	
	public ReportServiceImpl(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	@Override
	public List<Report> getReportsByUserId(int userId) {

		return reportRepository.findByUserId(userId);

	}

	@Override
	public Report getReportById(int id) {
		
		return reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException("日報が見つかりませんでした。(ID:"+ id + ")"));
	}

}
