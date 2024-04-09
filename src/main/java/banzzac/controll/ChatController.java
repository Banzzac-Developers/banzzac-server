package banzzac.controll;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.ChatDTO;
import banzzac.dto.ChatroomDTO;
import banzzac.mapper.ChatMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/chat")
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
		
		return res;
	}
	
	
	
}
