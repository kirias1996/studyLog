package com.example.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.study.service.ReportService;

@Controller
//@RequestMapping("/")
public class ReportController {

	private final ReportService reportService;

	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/reports")
	public String getReports(Model model) {
		
		/*テスト用にuserId = 1を決め打ちで入れている*/
		model.addAttribute("reports", reportService.getReportsByUserId(1));
		
		return "reports";
//		return "Hello World!";
	}
}
