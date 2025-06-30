package com.dadam.chat.mapper;

import java.util.List;

import com.dadam.chat.service.ChatVO;

public interface ChatMapper {
	
	public List<ChatVO> empList();
	//메인등록
	public int chatRoomMainAdd(ChatVO vo);
	//상세등록
	public int chatRoomDtlAdd(ChatVO vo);
	//채팅 리스트
	public List<ChatVO> chatList(String empId);
	//채팅 글 조회
	public List<ChatVO> selectChatMessages(String roomId);
	
}
