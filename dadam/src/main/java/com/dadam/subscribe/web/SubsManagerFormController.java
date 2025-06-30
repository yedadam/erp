package com.dadam.subscribe.web;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dadam.common.JasperDownCommon;

import jakarta.servlet.http.HttpServletResponse;

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
	
	@Autowired
	private JasperDownCommon jasperDownCommon;
	//등록
	@RequestMapping("/report")
	public ModelAndView report2(@RequestParam String subsCode , HttpServletResponse response) throws Exception
	{
		ModelAndView mv = new ModelAndView();
		//뷰객체
		mv.setView(jasperDownCommon);
		//fileName
		mv.addObject("filename", "/report/taxinvoice.jasper");
		//파라미터 담을 저장소
		HashMap<String, Object> map = new HashMap<>();
		//파라미터값
		map.put("p_subsCode", subsCode); 
		//파라미터값 전달
		mv.addObject("param", map);
		return mv;
	}
}
