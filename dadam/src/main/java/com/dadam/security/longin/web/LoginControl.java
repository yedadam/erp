package com.dadam.security.longin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.security.service.ErpUserVO;
import com.dadam.security.service.MainUserServiceImpl;
/* 
 * @author 신현욱
 * @since 2025.06.18
 * @desc 로그인 화면 스타일
 * @history
 *   - 2025.06.18 신현욱: 최초 작성
 */
@Controller
public class LoginControl {
	
	@Autowired
	MainUserServiceImpl service;
	
	@GetMapping("/main/login")
	public String mainLoginPage() {
		return "account/mainLogin";
	}
   @GetMapping("/erp/login")
     public String userLoginPage() {
	   return "account/login";
   }
   
   @GetMapping("/main/loginCheck/{userId}")
   @ResponseBody
   public int userCount(@PathVariable("userId") String userID ) {
	   return service.idCheck(userID);
   }
   
   @PostMapping("/main/addUser")
   @ResponseBody
   public int addUser(@RequestBody ErpUserVO vo) {
	   return service.insertId(vo);
   }
   
	
}
