package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.FriendDTO;

@Mapper
public interface FriendMapper {
	//* 친구리스트*/
	@Select("select * from friend where id=#{id} and block=1")
	List<FriendDTO> list(String id);
	//* 차단 친구 리스트 */
	@Select("select * from friend where id=#{id}  and block=0")
	List<FriendDTO> blockList(String id);
	//* 즐겨찾기 친구 리스트*/
	@Select("select * from friend where id=#{id}  and block=2")
	List<FriendDTO> favoriteList(String id);
	
	//* 친구차단 */
	@Update("update friend set block=0 where id=#{id} and friendId={friendId}") //차단,확인해야함
	String friendBlock(FriendDTO dto);
	//* 즐겨찾기친구추가 */
	@Update("update friend set block=2 where id=#{id} and friendId={friendId}") //즐찾 ,확인해야함
	String friendFavorite(FriendDTO dto);
	
	//*친구삭제*/
	@Delete("delete from friend where id=#{id} and friend_id=#{friendId}")
	int delete(FriendDTO dto);
	
	
		
}
		