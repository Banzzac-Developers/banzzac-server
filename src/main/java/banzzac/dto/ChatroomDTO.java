package banzzac.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ChatroomDTO {

	private int ChatroomNo;
	private String roomMember1, roomMember2, lastMessage;
}
