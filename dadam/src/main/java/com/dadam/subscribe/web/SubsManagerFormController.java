package com.dadam.subscribe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class SubsManagerFormController {
	
	@GetMapping("/subManager")
	public String subManagerForm() {
		
		return "/subscribe/manager";
	}
	
	@GetMapping("/preview")
	public String constPreview(@RequestParam String constImage) {
	    return "contracts/" + constImage.replace(".html", "");
	}
}
