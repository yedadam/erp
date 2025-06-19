package com.dadam.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dadam.sample.service.SampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



//controller 빈등록  
@Controller

//uri 경로에 erp는 무조건 넣고 sample 넣는 기능은 각자 부서를 넣으면 된다.
//예를들어 인사 :/erp/hr  영업:/erp/sales  회계:/erp/acc
//재고:/erp/inventory 기준정보:/erp/admin
@RequestMapping("/erp/sample")  
public class SampleFormController {
	
	//객체주입
	@Autowired
	SampleService sampleService;
	
	@GetMapping("/list")
	public String getMethodName() {
		return "sample/list";
	}
	
}
