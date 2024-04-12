package banzzac.rest;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.ChatDTO;
import banzzac.dto.ChatroomDTO;
import banzzac.mapper.ChatMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Resource
	ChatMapper mapper;
	
	@GetMapping("{userId}/{chatroomNo}")
	ArrayList<ChatDTO> chatList(@PathVariable int chatroomNo) {
		
		ArrayList<ChatDTO> res = mapper.getChatList(chatroomNo);
		
		return res;
	}
	
	@GetMapping("{userId}")
	ArrayList<ChatroomDTO> chatroomList(@PathVariable String userId){	//HttpSession session 멤버변수로 받아서 session.getAttribute("user").getId
		
		ArrayList<ChatroomDTO> res = mapper.getChatroomList(userId);
		System.out.println("채팅방 목록 뿌려주기");
		return res;
	}
	
	@PostMapping("makeChatroom")
	public ChatroomDTO insertChatroom(@RequestBody ChatroomDTO dto) {
		System.out.println("makeChatroom : "+dto);
		mapper.insertChatroom(dto);
	      
		return dto;
	}
	
	
	@PostMapping("send")
	public ChatDTO insertChat(@RequestBody ChatDTO dto) {
		System.out.println("insertChat : "+dto);
		mapper.insertChat(dto);
	      
		return dto;
	}

	
	
	
}
