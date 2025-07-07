package com.dadam.acc.account.web;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.acc.account.service.BalanceSheetDTO;
import com.dadam.acc.account.service.BalanceSheetService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/erp/accounting") 
public class BalanceSheetController {
	
	@Autowired BalanceSheetService balanceSheetService;
	
	@GetMapping("/balance")
	public String getBalanceSheet(@RequestParam Map<String, Object> param, Model model, HttpSession session) {
	    System.out.println("param" + param);

	    // comId 세션에서 가져오기
	    String comId = (String) session.getAttribute("comId");
	    param.put("comId", comId);

	    // 연도 기본값 설정
	    if (!param.containsKey("year") || param.get("year") == null || param.get("year").toString().isBlank()) {
	        param.put("year", String.valueOf(Year.now().getValue()));
	    }

	 // 재무상태표는 기본적으로 자산/부채/자본만
	    if (!param.containsKey("acctType") || param.get("acctType") == null || param.get("acctType").toString().isBlank()) {
	        param.put("acctType", "1,2,3"); 
	    }

	    // 연도 리스트
	    List<String> yearList = new ArrayList<>();
	    int currentYear = Year.now().getValue();
	    for (int i = currentYear - 5; i <= currentYear + 5; i++) {
	        yearList.add(String.valueOf(i));
	    }
	    model.addAttribute("yearList", yearList);

	    // 데이터 조회 및 모델에 담기
	    List<BalanceSheetDTO> list = balanceSheetService.selectBalanceSheet(param);
	    model.addAttribute("balanceList", list);
	    model.addAttribute("param", param);

	    return "acc/balanceSheet";
	}

    @GetMapping("/balanceData")
    @ResponseBody
    public List<BalanceSheetDTO> getBalanceSheetData(@RequestParam Map<String, Object> param, HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        param.put("comId", comId);
        
        if (!param.containsKey("acctType") || param.get("acctType") == null || param.get("acctType").toString().isBlank()) {
            param.put("acctType", "1,2,3");
        }
        
        return balanceSheetService.selectBalanceSheet(param);
    }

}
