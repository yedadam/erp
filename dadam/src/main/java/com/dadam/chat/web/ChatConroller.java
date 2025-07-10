package com.dadam.chat.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.chat.service.ChatMessageVO;
import com.dadam.chat.service.ChatService;
import com.dadam.chat.service.ChatVO;

@Controller
@RequestMapping("/erp")
public class ChatConroller {
	
	@Autowired
	ChatService service;
	
	 @Autowired
	 private SimpMessagingTemplate messagingTemplate;
	
	@GetMapping("/chat/chatEmpList")
	@ResponseBody
	public List<ChatVO> chatEmpList(){
		List<ChatVO> result= service.empList();
		return result;
	}
	//상세등록
	@PostMapping("/chat/chatDtlAdd")
	@ResponseBody
	public String chatDtlAdd(@RequestBody List<ChatVO> vo) {
		String result = service.chatRoomAdd(vo);
		return result;
	}
	//채팅목록조회
	@GetMapping("/chat/chatList")
	@ResponseBody
	public List<ChatVO> chatList(String empId){
		List<ChatVO> result = service.chatList(empId);
		return result;
	}
	
    @GetMapping("/chat/history")
    @ResponseBody
    public List<ChatVO> getChatHistory(@RequestParam String roomId) {
        return service.selectChatMessages(roomId);
    }
    
    @MessageMapping("/chat.{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessageVO chatMessage) {
    	//메시지 전송
    	chatMessage.setCreatedDate(new Date());
        messagingTemplate.convertAndSend("/topic/chat." + roomId, chatMessage);
        // 사용자 이름을 세션에 저장
        chatMessage.setChatId(roomId);
        //DB저장
        service.insertChatMessage(chatMessage);
        
    }
    
    //멤버 리스트
    @GetMapping("/chat/memberList")
    @ResponseBody
    public List<ChatVO> memberList(@RequestParam Map<String,Object> map){
    	List<ChatVO> result = service.memberList(map);
    	return result;
    }
}
