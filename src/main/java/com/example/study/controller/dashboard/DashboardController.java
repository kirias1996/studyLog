package com.example.study.controller.dashboard;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.study.entity.User;
import com.example.study.security.CustomUserDetails;
import com.example.study.security.LoginUserProvider;
import com.example.study.service.ReportService;

@Controller
public class DashboardController {
	private final ReportService reportService;
	private final LoginUserProvider loginUserProvider;
	
	public DashboardController(ReportService reportService,LoginUserProvider loginUserProvider) {
		this.reportService = reportService;
		this.loginUserProvider = loginUserProvider;
	}
	
	@GetMapping({"/dashboard","/"})
	public String showDashboard(@AuthenticationPrincipal CustomUserDetails userDetails,Model model) {
		User user = loginUserProvider.getLoginUser(userDetails);
		model.addAttribute("postCount", reportService.getTotalLearningDays(user.getId()));
	    model.addAttribute("totalStudyTime", reportService.getTotalLearningTimes(user.getId()));
	    model.addAttribute("streakCount", reportService.getConsecutiveLearningDays(user.getId()));
		return "dashboard/index";
	}
}
