package banzzac.dto;

import lombok.Data;

@Data
public class ChatroomDTO {

	private int ChatroomNo;
	private String roomMember1, roomMember2, lastMessage;
}
