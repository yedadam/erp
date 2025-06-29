package com.dadam.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.dadam.chat.mapper.ChatMapper;
import com.dadam.chat.service.ChatService;
import com.dadam.chat.service.ChatVO;

@Service
public class CharServiceImpl implements ChatService{
	
	@Autowired
	ChatMapper mapper;
	
	@Override
	public List<ChatVO> empList() {
	    List<ChatVO> result = mapper.empList();
		return result;
	}
	
	@Transactional
	@Override
	public int chatRoomAdd(List<ChatVO>vo) {
		int result = 0;
		ChatVO mainVo = vo.get(0);
		result = mapper.chatRoomMainAdd(mainVo);
		for(ChatVO param : vo) {
			param.setChatId(mainVo.getChatId());
			result += mapper.chatRoomDtlAdd(param);
		}
		return result;
	}
	
	@Override
	public List<ChatVO> chatList(String empId) {
	    List<ChatVO> result  = mapper.chatList(empId);
		return result;
	}
	
	//채팅조회
	@Override
	public List<ChatVO> selectChatMessages(String roomId) {
		List<ChatVO> result = mapper.selectChatMessages(roomId);
		return result;
	}
	
}
