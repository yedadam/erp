package com.dadam.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

//모든 컨트롤러에 모델을 주입하기 위해 설정
@ControllerAdvice
public class AttrController {
	
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
}
