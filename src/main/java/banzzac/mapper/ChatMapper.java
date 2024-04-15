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
			+ "(#{senderId},#{receiverId},#{message}, sysdate(), #{sendImg}, #{chatroomNo} ) ")
	int insertChat(ChatDTO dto);
	
	
	@Select("SELECT "
			+ "    c.*, "
			+ "    CASE "
			+ "        WHEN c.room_member_1 = #{userId} THEN m2.id "
			+ "        WHEN c.room_member_2 = #{userId} THEN m1.id "
			+ "    END AS opponent_id, "
			+ "    CASE "
			+ "        WHEN c.room_member_1 = #{userId} THEN m2.nickname"
			+ "        WHEN c.room_member_2 = #{userId} THEN m1.nickname"
			+ "    END AS member_nickname, "
			+ "    CASE "
			+ "        WHEN c.room_member_1 = #{userId} THEN m2.img "
			+ "        WHEN c.room_member_2 = #{userId} THEN m1.img "
			+ "    END AS member_img, "
			+ "    CASE "
			+ "        WHEN c.room_member_1 = #{userId} THEN d2.name "
			+ "        WHEN c.room_member_2 = #{userId} THEN d1.name "
			+ "    END AS dog_name, "
			+ "    CASE "
			+ "        WHEN c.room_member_1 = #{userId} THEN d2.img "
			+ "        WHEN c.room_member_2 = #{userId} THEN d1.img "
			+ "    END AS dog_img, "
			+ "    COUNT(cm.is_read) AS unread_messages_count "
			+ "FROM "
			+ "    chatroom c "
			+ "LEFT OUTER JOIN "
			+ "    member m1 ON m1.id = c.room_member_1 "
			+ "LEFT OUTER JOIN "
			+ "    member m2 ON m2.id = c.room_member_2 "
			+ "LEFT OUTER JOIN "
			+ "    dog d1 ON d1.id = c.room_member_1 "
			+ "LEFT OUTER JOIN "
			+ "    dog d2 ON d2.id = c.room_member_2 "
			+ "LEFT OUTER JOIN "
			+ "    chat_message cm ON cm.chatroom_no = c.chatroom_no AND cm.is_read = 1 "
			+ "WHERE "
			+ "    c.room_member_1 = #{userId} OR c.room_member_2 = #{userId} "
			+ "GROUP BY "
			+ "    c.chatroom_no "
			+ "ORDER BY "
			+ "    c.last_message_sendtime DESC " )
	ArrayList<ChatroomDTO> getChatroomList(String userId); 
	
	
	@Insert("insert into chatroom "
			+ "(room_member_1, room_member_2) values "
			+ "(#{roomMember1},#{roomMember1} ) ")
	int insertChatroom(ChatroomDTO dto);
	
}
