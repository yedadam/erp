package com.dadam.chat.service;

import java.util.List;

public interface ChatService {
    //사원목록조회
	public List<ChatVO> empList();
	//상세등록
	public int chatRoomAdd(List<ChatVO> vo);
	//채팅 리스트
	public List<ChatVO> chatList(String empId);
	//채팅 글 조회
	public List<ChatVO> selectChatMessages(String roomId);
}
