package com.dadam.acc.account.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dadam.acc.account.service.AccountService;
import org.springframework.web.bind.annotation.RequestParam;


//controller 빈등록  
@Controller

//uri 경로에 erp는 무조건 넣고 sample 넣는 기능은 각자 부서를 넣으면 된다.
//예를들어 인사 :/erp/hr  영업:/erp/sales  회계:/erp/acc
//재고:/erp/inventory 기준정보:/erp/admin
@RequestMapping("/erp/accounting")  
public class AccountFormController {
	
	
	@GetMapping("/account")
	public String getAccount() {
		return "acc/account";
	}
	
	@GetMapping("/chit")
	public String getChit() {
		return "acc/chit";
	}
	@GetMapping("/create")
	public String getCreate() {
		return "acc/create";
	}
	

    @GetMapping("/autoRule")
    public String openForm() {
        return "acc/autoRuleForm :: fragment";  // templates/acc/autoRuleForm.html
    }
    
    @GetMapping("/balanceSheet")
    public String getBalanceSheet() {
        return "acc/balanceSheet";  
    }
    
    @GetMapping("/profitStatement")
    public String getProfitStatement() {
        return "acc/profitStatement";  
    }
	
}
