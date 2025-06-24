package com.dadam.acc.credit.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.acc.credit.service.CreditService;
import com.dadam.acc.credit.service.CreditVO;

@RestController
@RequestMapping("/erp/credit")
public class CreditRestController {
	
	@Autowired
	CreditService creditServic;
	
	@GetMapping("/creditFindAll")
	public List<CreditVO> getCredit() {
		return creditServic.creditFindAll();
	}
	

}
