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
public class ProfitStatementController {
	
	
	@Autowired BalanceSheetService balanceSheetService;

    // 손익계산서 화면 렌더링
    @GetMapping("/profit")
    public String getProfitStatementPage(@RequestParam Map<String, Object> param, Model model, HttpSession session) {

        // 회사 ID 세션에서 가져오기
        String comId = (String) session.getAttribute("comId");
        param.put("comId", comId);

        // 연도 기본값 설정
        if (!param.containsKey("year") || param.get("year") == null || param.get("year").toString().isBlank()) {
            param.put("year", String.valueOf(Year.now().getValue()));
        }

        // 연도 리스트 생성 (앞뒤 5년)
        List<String> yearList = new ArrayList<>();
        int currentYear = Year.now().getValue();
        for (int i = currentYear - 5; i <= currentYear + 5; i++) {
            yearList.add(String.valueOf(i));
        }

        model.addAttribute("yearList", yearList);
        model.addAttribute("param", param);

        return "acc/profitStatement"; // 템플릿 경로
    }

    // 손익계산서 JSON 데이터 조회
    @GetMapping("/profitData")
    @ResponseBody
    public List<BalanceSheetDTO> getProfitData(@RequestParam Map<String, Object> param,
                                               HttpSession session) {

        String comId = (String) session.getAttribute("comId");
        param.put("comId", comId);

        // 수익(4), 비용(5) 계정만 필터
        param.put("acctType", "4,5");

        return balanceSheetService.selectBalanceSheet(param);
    }
}
