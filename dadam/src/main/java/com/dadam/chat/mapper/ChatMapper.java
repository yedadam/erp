package com.dadam.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.chat.service.ChatMessageVO;
import com.dadam.chat.service.ChatVO;
import com.dadam.standard.vender.service.VenderVO;

public interface ChatMapper {
	
	public List<ChatVO> empList(String comId);
	//메인등록
	public int chatRoomMainAdd(ChatVO vo);
	//상세등록
	public int chatRoomDtlAdd(ChatVO vo);
	//채팅 리스트
	public List<ChatVO> chatList(@Param("empId") String empId, @Param("comId") String comId);
	//채팅 글 조회
	public List<ChatVO> selectChatMessages(@Param("chatId") String roomId,@Param("comId") String comId);
	//채팅 메시지 등록
	public int insertChatMessage(ChatMessageVO vo);
	
}
