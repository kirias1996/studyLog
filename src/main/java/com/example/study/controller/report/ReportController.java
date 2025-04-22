package com.example.study.controller.report;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;
import com.example.study.entity.User;
import com.example.study.security.CustomUserDetails;
import com.example.study.security.LoginUserProvider;
import com.example.study.service.ReportService;

@Controller
public class ReportController {

	private final ReportService reportService;
	private final LoginUserProvider loginUserProvider;

	public ReportController(ReportService reportService, LoginUserProvider loginUserProvider) {
		this.reportService = reportService;
		this.loginUserProvider = loginUserProvider;
	}

	@GetMapping("/reports")
	public String getReports(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

		/*テスト用にuserId = 1を決め打ちで入れている*/
		User user = loginUserProvider.getLoginUser(userDetails);

		model.addAttribute("reports", reportService.getReportsByUserId(user.getId()));

		return "reports";
	}

	/*@PathVariableでGETリクエストのパス{id}を引数で取得する*/
	@GetMapping("/reports/{id}")
	public String getReport(@PathVariable int id, Model model) {
		model.addAttribute("report", reportService.getReportById(id));
		return "report-detail";
	}

	@GetMapping("/reports/create")
	public String showCreateForm(Model model) {
		model.addAttribute("reportRequestDto", new ReportRequestDto());
		model.addAttribute("isEdit", false);
		return "report-form";
	}

	/* 
	 * @ModelAttributeを利用することで自動で値を設定可能
	 * 入力フォームのname属性とDTOのフィールド名が対応していることが条件
	 * エラー時でもDTOがモデルにセットされているので再入力の必要がない 
	 * */
	@PostMapping("/reports")
	public String createReport(@ModelAttribute @Valid ReportRequestDto dto, BindingResult result,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUserDetails userDetails) {
		if (result.hasErrors()) {
			return "report-form";
		}
		dto.setUserId(loginUserProvider.getLoginUser(userDetails).getId());
		reportService.createReport(dto);
		redirectAttributes.addFlashAttribute("successMessage", "日報の登録が完了しました。");

		return "redirect:/reports";
	}

	@GetMapping("/reports/edit/{id}")
	public String showEditForm(@PathVariable int id, Model model) {
		Report report = reportService.getReportById(id);
		model.addAttribute("reportRequestDto", reportService.toReportRequestDto(report));
		model.addAttribute("isEdit", true);

		return "report-form";
	}

	@PutMapping("/reports")
	public String editReport(@ModelAttribute @Valid ReportRequestDto dto, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "report-form";
		}
		reportService.updateReport(dto);
		redirectAttributes.addFlashAttribute("successMessage", "日報の編集が完了しました。");

		return "redirect:/reports";
	}

	@DeleteMapping("/reports/{id}")
	public String deleteReport(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
		if (!reportService.existById(id)) {
			redirectAttributes.addFlashAttribute("errorMessage", "指定された日報が見つかりませんでした。");
			return "redirect:/reports";
		}
		reportService.deleteReport(id);
		redirectAttributes.addFlashAttribute("successMessage", "日報を削除しました。");
		return "redirect:/reports";
	}

}