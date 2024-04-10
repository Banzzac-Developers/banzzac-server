package banzzac.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ChatDTO {

	private String senderId , receiverId , message , sendImg ;
	private int isRead , chatroomNo;
	private Date sendTime;
	
	
}
