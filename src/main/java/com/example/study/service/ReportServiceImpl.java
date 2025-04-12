package com.example.study.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;
import com.example.study.exception.ReportNotFoundException;
import com.example.study.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

	private final double MINUTES_PER_HOUR = 60.0;
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

		return reportRepository.findById(id)
				.orElseThrow(() -> new ReportNotFoundException("日報が見つかりませんでした。(ID:" + id + ")"));
	}

	@Transactional
	public void createReport(ReportRequestDto dto) {
		Report report = new Report();
		report.setTitle(dto.getTitle());
		report.setContent(dto.getContent());
		report.setLearningDate(dto.getLearningDate());

		//〇時間〇分→〇.〇hに変換して設定(DB定義に合わせるため)
		report.setLearningHours(dto.getLearningHours() + (dto.getLearningMinutes() / MINUTES_PER_HOUR));

		//認証機能が作成できるまではuserId=1で固定
		report.setUserId(1);
		//タグ自動登録機能を実装するまではtagId=1で固定
		report.setTagId(1);

		reportRepository.save(report);
	}

}
