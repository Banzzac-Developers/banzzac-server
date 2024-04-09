package banzzac.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ChatDTO {

	private String senderId , receiverId , message , sendImg ;
	private int isRead , chatroomNo;
	private Date sendTime;
	
	
}
