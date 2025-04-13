package com.example.study.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;
import com.example.study.exception.ReportNotFoundException;
import com.example.study.repository.ReportRepository;
import com.example.study.util.TimeConverter;

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

		report.setUpdatedAt(LocalDateTime.now());
		report.setCreatedAt(LocalDateTime.now());

		//認証機能が作成できるまではuserId=1で固定
		report.setUserId(1);
		//タグ自動登録機能を実装するまではtagId=1で固定
		report.setTagId(1);

		reportRepository.save(report);
	}

	@Override
	public ReportRequestDto toReportRequestDto(Report report) {
		ReportRequestDto reportRequestDto = new ReportRequestDto();
		reportRequestDto.setId(report.getId());
		//〇.〇h→〇時間〇分に変換(画面表示に合わせるため)
		reportRequestDto.setTitle(report.getTitle());
		reportRequestDto.setContent(report.getContent());
		reportRequestDto.setLearningDate(report.getLearningDate());
		reportRequestDto.setLearningHours(TimeConverter.getHour(report.getLearningHours()));
		reportRequestDto.setLearningMinutes(TimeConverter.getMinute(report.getLearningHours()));

		return reportRequestDto;
	}

	@Override
	public void updateReport(ReportRequestDto dto) {
		Report report = reportRepository.findById(dto.getId()).orElseThrow(
				() -> new ReportNotFoundException("日報が見つかりませんでした。(ID:" + dto.getId() + ")"));;
		report.setTitle(dto.getTitle());
		report.setContent(dto.getContent());
		report.setLearningDate(dto.getLearningDate());

		//〇時間〇分→〇.〇hに変換して設定(DB定義に合わせるため)
		report.setLearningHours(dto.getLearningHours() + (dto.getLearningMinutes() / MINUTES_PER_HOUR));
		report.setUpdatedAt(LocalDateTime.now());

		reportRepository.save(report);

	}

}
