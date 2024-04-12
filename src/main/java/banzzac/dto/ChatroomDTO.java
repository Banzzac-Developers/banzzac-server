package banzzac.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ChatroomDTO {

	private int ChatroomNo, unreadMessagesCount;
	private String roomMember1, roomMember2, lastMessage, 
				opponentId, memberNickname, memberImg,
				dogName, dogImg, lastMessageSendtimeStr;
	private Date lastMessageSendtime;
	
	   public String getLastMessageSendtimeStr() {
		   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	      return sdf.format(lastMessageSendtime);
	   }
//	   public void setLastMessageSendtimeStr(String lastMessageSendtimeStr) {
//	      try {
//	         this.lastMessageSendtime = sdf.parse(lastMessageSendtimeStr);
//	      } catch (Exception e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      }
//	   }
	
}
