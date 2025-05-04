package com.example.study.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		model.addAttribute("postCount", 5);
	    model.addAttribute("totalStudyTime", "10h");
	    model.addAttribute("streakCount", 3);
		return "dashboard/index";
	}
}
