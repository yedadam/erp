package com.dadam.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dadam.common.mapper.CodeMapper;
import com.dadam.common.service.CodeVO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.dadam.inventory.settlement.service.SettlementService;

import jakarta.servlet.http.HttpServletRequest;

//모든 컨트롤러에 모델을 주입하기 위해 설정
@ControllerAdvice
@RestController
public class AttrController {
	  @Autowired
	  SettlementService service;
	  //return을 하면 actvieMenu에 key값에 저장
	  @ModelAttribute("activeMenu")
	  public String setAttriMent(HttpServletRequest req) {
		  //uri 파악
		  String uri = req.getRequestURI();
		  //uri 시작을 배열로 나눔 예를들어 erp/standard -> ["","erp","standard"]
		  String[] parts = uri.split("/"); 
		  //경로가 3개 이상인 경우 3번째 인덱스 반환
		  if(parts.length >= 3) {
			  return parts[2];
		  }
		  return "home";
	  }
	  
	  @ModelAttribute("elecCount")
	  public int getEleCount(HttpServletRequest req) {
		  String uri = req.getRequestURI();
		 
		  if(uri.startsWith("/erp")) {
			  return service.eleList();
		  }
		  return 0;
	  }

    @Autowired
    private CodeMapper codeMapper;

    /**
     * 공통코드 조회 API
     * 예: /common/codes?mainCode=emp
     */
    @GetMapping("/common/codes")
    public List<CodeVO> getCodes(@RequestParam String mainCode) {
        return codeMapper.selectCode(mainCode);
    }
}
