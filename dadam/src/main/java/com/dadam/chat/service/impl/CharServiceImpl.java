package com.dadam.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.chat.mapper.ChatMapper;
import com.dadam.chat.service.ChatMessageVO;
import com.dadam.chat.service.ChatService;
import com.dadam.chat.service.ChatVO;
import com.dadam.security.service.LoginUserAuthority;
import com.dadam.standard.vender.service.VenderVO;

@Service
public class CharServiceImpl implements ChatService{
	
	//comName 가져오기
	String comId = "123123";
	public void initAuthInfo() {  
		//로그인 객체값 연결
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//로그인 객체 가져오기
		Object principal = auth.getPrincipal(); 
		
		if (principal instanceof LoginUserAuthority) {
			LoginUserAuthority user = (LoginUserAuthority) principal;
			comId = user.getComId();
			System.out.println("회사명: " + comId);
		}
	}
	@Autowired
	ChatMapper mapper;
	@Override
	public List<ChatVO> empList() {
		initAuthInfo();
	    List<ChatVO> result = mapper.empList(comId);
		return result;
	}
	
	//채팅방등록
	@Transactional
	@Override
	public String chatRoomAdd(List<ChatVO>vo) {
		initAuthInfo();
		int result = 0;
		vo.get(0).setComId(comId);
		ChatVO mainVo = vo.get(0);
		result = mapper.chatRoomMainAdd(mainVo);
		for(ChatVO param : vo) {
			param.setComId(comId);
			param.setChatId(mainVo.getChatId());
			result += mapper.chatRoomDtlAdd(param);
		}
		return mainVo.getChatId();
	}
	
	@Override
	public List<ChatVO> chatList(String empId) {
		initAuthInfo();
	    List<ChatVO> result  = mapper.chatList(empId,comId);
		return result;
	}
	
	//채팅조회
	@Override
	public List<ChatVO> selectChatMessages(String roomId) {
		initAuthInfo();
		List<ChatVO> result = mapper.selectChatMessages(roomId,comId);
		return result;
	}
	
	//채팅메시지 등록
	@Override
	public int insertChatMessage(ChatMessageVO vo) {
	    int result = mapper.insertChatMessage(vo);
		return result;
	}
	
	@Override
	public List<ChatVO> memberList(Map<String, Object> map) {
		map.put("comId", comId);
		List<ChatVO> result = mapper.memberList(map);
		return result;
	}
}
