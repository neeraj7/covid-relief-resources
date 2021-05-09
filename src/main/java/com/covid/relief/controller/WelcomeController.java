package com.covid.relief.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.covid.relief.open.nlp.SentenceDetectorNLP;
import com.covid.relief.service.TwitterService;

@Controller
@RequestMapping("/")
public class WelcomeController {
	
	@Autowired
	private SentenceDetectorNLP nlp;
	
	@Autowired
	private TwitterService service;

//	@GetMapping
//	public String welcomeHome(Model model) {
//		model.addAttribute("tweets", service.getAllSavedTweets(null, null));
//		return "index";
//	}
	
	@GetMapping("/resources")
	public Set<String> getResources() {
		return nlp.extractResources("RRM Hospital, Delhi\\n● Contact- 9891761679,8287595266\\nICU bed available (patient's oxygen level should be above 85)\\n● Life hospital, Sector 8, Faridabad, Delhi NCR\\n● Contact- +91 83968 26566\\n● Verified time-- 8:28 PM , 07/06/2021");
	}
}