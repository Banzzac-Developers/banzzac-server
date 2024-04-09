package banzzac.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.ChatDTO;
import banzzac.dto.ChatroomDTO;

@Mapper
public interface ChatMapper {

	@Select("select * from chat_message where chatroom_no = #{chatroomNo} order by send_time")
	ArrayList<ChatDTO> getChatList(int chatroomNo); 
	
	
	@Insert("insert into chat_message "
			+ "(sender_id, receiver_id, message, send_time, send_img, chatroom_no) values "
			+ "(#{senderId},#{receiverId},#{message}, sysdate()), #{sendImg}, #{chatroomNo} ")
	int insert(ChatDTO dto);
	
	
	@Select("select * from chatroom where room_member_1 = #{userId} or room_member_2 = #{userId} order by last_message_sendtime desc" )
	ArrayList<ChatroomDTO> getChatroomList(String userId); 
	
}
