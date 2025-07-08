package com.dadam.acc.credit.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.acc.credit.service.CreditService;
import com.dadam.acc.credit.service.CreditVO;

@RestController
@RequestMapping("/erp/accounting")
public class CreditRestController {
	
	@Autowired
	CreditService creditServic;
	
	@GetMapping("/creditFindAll")
	public List<CreditVO> getCredit(String comId) {
		return creditServic.creditFindAll(comId);
	}
	
	@GetMapping("/creditFindByCode")
	@ResponseBody
	public List<CreditVO> getCreditDtl(@RequestParam String vdrCode, String comId){
		System.out.println("vdr코드는" + vdrCode);
		List<CreditVO> result = creditServic.creditFindCode(vdrCode, comId);
		return result;
	}
	

}
