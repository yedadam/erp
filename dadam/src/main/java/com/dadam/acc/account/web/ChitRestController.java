package com.dadam.acc.account.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/chtTypeFindByCode")
	public List<Map<String, String>> chtTypeFindByCode(@RequestParam("keyword") String keyword){
		return chitService.chtTypeFindByCode(keyword);
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

    // 2. 자동분개 규칙 저장
    @PostMapping("/ruleSave")
    @ResponseBody
    public Map<String, Object> saveAutoRules(@RequestBody List<ChitVO> rules) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("저장되는 규칙 " +result);
        try {
            chitService.saveAllRules(rules);
            result.put("result", "success");
            result.put("message", "자동분개 규칙이 저장되었습니다.");
        } catch (IllegalArgumentException e) {
            result.put("result", "fail");
            result.put("message", "오류: " + e.getMessage());
        }

        return result;
    }
    

    /**
     * 자동분개 규칙 조회
     * @param chitType 거래유형코드 (예: "cht01")
     * @param comId 회사 ID (예: "com-101")
     * @return 규칙 목록 (차/대변 + 계정과목 코드)ruleByChitType
     */
    @GetMapping("/ruleByChitType")
    public ResponseEntity<List<Map<String, Object>>> getAutoRules( @RequestParam String chitType, @RequestParam String comId) {
        List<Map<String, Object>> rules = chitService.getAutoRules(chitType, comId);
        return ResponseEntity.ok(rules);
    }
   
    // 자동분개 규칙 일괄 저장 (Account 방식)
    @PostMapping("/ruleSaveAll")
    @ResponseBody
    public Map<String, Object> saveAutoRulesAll(@RequestBody Map<String, List<ChitVO>> payload) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ChitVO> createdRows = payload.get("createdRows");
            List<ChitVO> updatedRows = payload.get("updatedRows");
            List<ChitVO> deletedRows = payload.get("deletedRows");
            chitService.saveAllRulesSeparated(createdRows, updatedRows, deletedRows);
            result.put("result", "success");
            result.put("message", "자동분개 규칙이 저장되었습니다.");
        } catch (IllegalArgumentException e) {
            result.put("result", "fail");
            result.put("message", "오류: " + e.getMessage());
        }
        return result;
    }
    
    

    @GetMapping("/chitSearch")
    public List<ChitVO> chitSearch(@RequestParam Map<String, Object> params) {
        return chitService.chitSearch(params);
    }

    // 전표코드/거래처/전표명 자동완성
    @GetMapping("/chitAutoComplete")
    @ResponseBody
    public List<Map<String, String>> chitAutoComplete(@RequestParam String type, @RequestParam String value) {
        // comId는 인증에서 추출
        return chitService.chitAutoComplete(type, value);
    }
}
