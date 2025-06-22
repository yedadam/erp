package com.dadam.subscribe.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubsManagerService;

/* 
 * @author 신현욱
 * @since 2025.06.21
 * @desc 유저 구독 정보 조회
 * @history
 *   - 2025.06.21 신현욱: 최초 작성
 */

@RequestMapping("/main")
@RestController
public class SubsManagerRestController {
	
	@Autowired
	SubsManagerService service;
	
	//유저 구독 정보조회 
	@GetMapping("/userList")
	public List<ErpUsersVO> userList() {
		List<ErpUsersVO> result = service.erpUserList();
		return result;
	}
	
	@GetMapping("/subsDetailList")
	public List<SubsListVO> subsList(@RequestParam String param){
		List<SubsListVO> result = service.subsList(param);
		return result;
	}
	
	//수정
	@PutMapping("/subsModify/{comId}")
	public int subsModify(@PathVariable String comId, @RequestBody ErpUsersVO info) {
			System.out.println("comId" +comId);
			System.out.println(info);
		return 1;
	}
}
