package com.dadam.acc.credit.web;

import java.util.List;
import java.util.Map;

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
	public List<CreditVO> getCredit(@RequestParam String comId, @RequestParam(required = false) String type) {
		return creditServic.creditFindAll(comId, type);
	}
	
	@GetMapping("/creditFindByCode")
	@ResponseBody
	public List<CreditVO> getCreditDtl(@RequestParam String vdrCode, String comId){
		System.out.println("vdr코드는" + vdrCode);
		List<CreditVO> result = creditServic.creditFindCode(vdrCode, comId);
		return result;
	}
	
	@GetMapping("/creditSearch")
	public List<CreditVO> creditSearch(@RequestParam Map<String, Object> params) {
		return creditServic.creditSearch(params);
	}

	@GetMapping("/detailSearch")
	public List<CreditVO> detailFindSupplier(@RequestParam String vdrCode, @RequestParam String comId) {
		List<CreditVO> result = creditServic.detailFindSupplier(vdrCode, comId);
		return result;
	}

	@GetMapping("/purchaseSummary")
	public List<Map<String, Object>> purchaseSummary(@RequestParam String vdrCode, @RequestParam String comId) {
		return creditServic.purchaseSummary(vdrCode, comId);
	}

	@GetMapping("/purchaseDetail")
	public List<Map<String, Object>> purchaseDetail(@RequestParam String purOrdCode) {
		return creditServic.purchaseDetail(purOrdCode);
	}

}
