package com.dadam.acc.account.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.acc.account.service.ChitService;
import com.dadam.acc.account.service.ChitVO;

@RestController
@RequestMapping("/erp/accounting")
public class ChitRestController {
	
	@Autowired ChitService chitService;
	
	@GetMapping("/chitFindAll")
	public List<ChitVO> getChitFindAll(){
		return chitService.chitFindAll();
	}
	
	@GetMapping("/acctCodeFind")
    public List<Map<String, String>> acctCodeFind(@RequestParam("keyword") String keyword) {
        return chitService.acctCodeFindByCode(keyword);
    }
	
	@GetMapping("/indTypeFindByCode")
	public List<Map<String, String>> indTypeFindByCode(@RequestParam("keyword") String keyword){
		return chitService.indTypeFindByCode(keyword);
	}
	
	@GetMapping("/chitTypeFindByCode")
	public List<Map<String, String>> chitTypeFindByCode(@RequestParam("keyword") String keyword){
		return chitService.chitTypeFindByCode(keyword);
	}
	
	@PostMapping("/chitSaveAll")
	@ResponseBody
	public Map<String, Object> saveAccounts(@RequestBody ChitVO chit) {
	    Map<String, Object> result = new HashMap<>();

	    try {
	    	chitService.saveAll(chit);
	        result.put("result", "success");
	        result.put("message", "저장이 완료되었습니다.");
	    } catch (IllegalArgumentException e) {
	        result.put("result", "fail");
	        result.put("message", "유효성 오류: " + e.getMessage());
	    }

	    return result;
	}
	
	@PostMapping("/modifyChitPay")
	@ResponseBody
	public Map<String, Object> confirmPayments(@RequestBody List<Map<String, Object>> payload) {
	    System.out.println(payload);
		for (Map<String, Object> row : payload) {
	        String chitCode = (String) row.get("chitCode");
	        String articleCode = (String) row.get("articleCode");
	        int totPrice = Integer.parseInt(row.get("totPrice").toString());

	        chitService.modifyChitPay(chitCode, articleCode, totPrice);
	    }
	    return Map.of("result", "success", "message", payload.size() + "건 결제 완료");
	}
	
    // 1. 자동분개 규칙 전체 조회
    @GetMapping("/ruleList")
    public List<ChitVO> getAutoChitRules(@RequestParam String comId) {
        return chitService.getAutoChitRules(comId);
    }
/*
    // 2. 자동분개 규칙 저장
    @PostMapping("/entry-rule/saveAll")
    public ResponseEntity<String> saveAutoChitRules(@RequestBody List<ChitVO> rules) {
        chitService.saveAutoChitRules(rules);
        return ResponseEntity.ok("saved");
    }
*/	
}
