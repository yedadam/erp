package com.dadam.standard.item.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dadam.security.service.LoginUserAuthority;

@Controller
@RequestMapping("/erp/standard")
public class ChitStandardFormComtroller {
	
	String comId = "com-101";
    String deptCode = "de1001";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            deptCode = user.getDeptCode();
        }
    }
	
	@GetMapping("/chit")
	public String chitListForm(Model model) {
		initAuthInfo();
		model.addAttribute("deptId",deptCode);
		return "/standard/chit";
	}
}
