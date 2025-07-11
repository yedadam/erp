package com.dadam.chat.service;

import java.util.List;
import java.util.Map;


public interface ChatService {
    //사원목록조회
	public List<ChatVO> empList();
	//상세등록
	public String chatRoomAdd(List<ChatVO> vo);
	//채팅 리스트
	public List<ChatVO> chatList(String empId);
	//채팅 글 조회
	public List<ChatVO> selectChatMessages(String roomId);
	//채팅 메시지 등록
	public int insertChatMessage(ChatMessageVO vo);
    //채팅 멤버 리스트
	public List<ChatVO> memberList(Map<String,Object> map);
}
