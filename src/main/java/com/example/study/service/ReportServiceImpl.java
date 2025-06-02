package com.example.study.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;
import com.example.study.entity.Tag;
import com.example.study.exception.ReportNotFoundException;
import com.example.study.repository.ReportRepository;
import com.example.study.repository.TagRepository;
import com.example.study.util.TimeConverter;
import com.example.study.util.message.MessageUtil;
import com.example.study.validation.ReportTagValidator;

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
	private final TagRepository tagRepository;
	private final MessageUtil messageUtil;
	private final ReportTagValidator reportTagValidator;

	public ReportServiceImpl(ReportRepository reportRepository, TagRepository tagRepository, MessageUtil messageUtil,
			ReportTagValidator reportTagValidator) {
		this.reportRepository = reportRepository;
		this.tagRepository = tagRepository;
		this.messageUtil = messageUtil;
		this.reportTagValidator = reportTagValidator;
	}

	@Override
	public List<Report> getReportsByUserId(int userId) {

		return reportRepository.findByUserId(userId);

	}

	@Override
	public Report getUserOwnedReport(int reportId, int userId) {
		Report report = getReportById(reportId);
		validateOwnership(report, userId);
		return report;
	}

	@Override
	public Report getReportById(int id) {

		return reportRepository.findById(id)
				.orElseThrow(() -> new ReportNotFoundException(
						messageUtil.getMessage("reportNotFound.error", null, LocaleContextHolder.getLocale()) + "(ID:"
								+ id + ")"));
	}

	public void validateOwnership(Report report, int userId) {
		if (report.getUserId() != userId) {
			throw new AccessDeniedException(
					messageUtil.getMessage("reportAccessDenied.error", null, LocaleContextHolder.getLocale()));
		}
	}

	@Transactional
	public void createReport(ReportRequestDto dto) {
		//タグ名を小文字に変換して検索を実施(Java,javaが区別されないようDB登録時にすべて小文字変換して登録しているため)
		String cleanedTagName = normalizeTagName(dto.getTagName());

		/* Controllerでのバリデーションが適用されないケースに備えた再チェック
		   例)他のServiceクラスから直接呼び出された場合*/
		reportTagValidator.validateTagName(cleanedTagName);
		Tag tag = getTagForReport(tagRepository.findByTagName(cleanedTagName), cleanedTagName);

		Report report = new Report();
		report.setUserId(dto.getUserId());
		report.setTitle(dto.getTitle());
		report.setContent(dto.getContent());
		report.setLearningDate(dto.getLearningDate());

		//〇時間〇分→〇.〇hに変換して設定(DB定義に合わせるため)
		report.setLearningHours(dto.getLearningHours() + (dto.getLearningMinutes() / MINUTES_PER_HOUR));

		report.setUpdatedAt(LocalDateTime.now());
		report.setCreatedAt(LocalDateTime.now());

		//既存のタグ名が見つかれば取得した値をReportクラスにセットし登録
		report.setTag(tag);

		reportRepository.save(report);
	}

	private Tag getTagForReport(Tag tag, String cleanedTagName) {

		//タグ名が見つからなければタグ登録を実施&新規登録したレコード情報を再取得
		if (Objects.isNull(tag)) {
			Tag newTag = new Tag();
			newTag.setTagName(cleanedTagName);
			try {
				tag = tagRepository.save(newTag);
			} catch (DataIntegrityViolationException e) {
				tag = tagRepository.findByTagName(cleanedTagName);
			}
		}
		return tag;
	}

	private String normalizeTagName(String tagName) {
		return tagName.trim().toLowerCase();
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
		reportRequestDto.setDisplayLearningTimes(report.getLearningHours());
		reportRequestDto.setTagId(report.getTag().getTagId());
		reportRequestDto.setTagName(report.getTag().getTagName());

		return reportRequestDto;
	}

	@Override
	@Transactional
	public void updateReport(ReportRequestDto dto, int userId) {
		String cleanedTagName = normalizeTagName(dto.getTagName());
		reportTagValidator.validateTagName(cleanedTagName);
		// dtoについてはControllerクラスにてバリデーションチェック済み
		Report report = getReportById(dto.getId());
		validateOwnership(report, userId);

		Tag tag = getTagForReport(tagRepository.findByTagName(cleanedTagName), cleanedTagName);

		report.setTitle(dto.getTitle());
		report.setContent(dto.getContent());
		report.setLearningDate(dto.getLearningDate());
		report.setTag(tag);

		//〇時間〇分→〇.〇hに変換して設定(DB定義に合わせるため)
		report.setLearningHours(dto.getLearningHours() + (dto.getLearningMinutes() / MINUTES_PER_HOUR));
		report.setUpdatedAt(LocalDateTime.now());

		reportRepository.save(report);

	}

	@Override
	public void deleteReport(int reportId, int userId) {
		Report report = getReportById(reportId);
		validateOwnership(report, userId);
		reportRepository.delete(report);

	}

	@Override
	public boolean existById(int id) {
		return reportRepository.existsById(id);
	}

	@Override
	public double getTotalLearningTimes(int userId) {
		Double result = reportRepository.findTotalLearningTimes(userId);

		if (result == null) {
			return 0.0;
		}
		return result;
	}

	@Override
	public int getTotalLearningDays(int userId) {
		Integer result = reportRepository.findTotalLearningDays(userId);
		if (result == null) {
			return 0;
		}

		return result;
	}

	@Override
	public int getConsecutiveLearningDays(int userId) {
		List<LocalDate> learningDays = reportRepository.findLearningDays(userId);

		if (learningDays.isEmpty()) {
			return 0;
		}

		LocalDate prevDate = learningDays.get(0);
		int consecutiveCount = 1;

		for (int i = 1; i < learningDays.size(); i++) {
			if (learningDays.get(i).equals(prevDate.minusDays(1))) {
				consecutiveCount++;
				prevDate = learningDays.get(i);

			} else {
				return consecutiveCount;
			}

		}
		return consecutiveCount;
	}

}
