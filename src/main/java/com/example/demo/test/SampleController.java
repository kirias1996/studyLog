package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {
	TestService testService;
	
	@Autowired
	public SampleController(TestService testService) {
		this.testService = testService;
	}
	
	
	@GetMapping
	public String sample(Model model) {
		model.addAttribute("title", "Hello World");
		return "index";
	}
	
	@GetMapping("/dbtest")
	public String dbTest(Model model) {
		List<Test> testList = testService.getAll();
		
		model.addAttribute("testList", testList);
		model.addAttribute("title", "Hello World");
		
		return "index";
	}
}
