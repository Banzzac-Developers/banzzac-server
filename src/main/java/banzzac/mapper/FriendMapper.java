package banzzac.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.FriendDTO;
import banzzac.dto.MemberDTO;


@Mapper
public interface FriendMapper {
	//* 친구리스트*/
	@Select("select * from friend where id=#{id} and block=1 or block=2 ")
	List<FriendDTO> list(String id);
	//* 차단 친구 리스트 */
	@Select("select * from friend where id=#{id}  and block=0")
	List<FriendDTO> blockList(String id);
	//* 즐겨찾기 친구 리스트*/
	@Select("select * from friend where id=#{id}  and block=2")
	List<FriendDTO> favoriteList(String id);
	
	//* 친구차단 */
	@Update("update friend set block=0 where id=#{id} and friendId={friendId}") //차단
	int friendBlock(FriendDTO dto);
	
	//* 친구차단해제 */
	@Update("update friend set block=1 where id=#{id} and friendId={friendId}") //차단해제
	int friendUnBlock(FriendDTO dto);
	
	
	//* 즐겨찾기친구추가 */
	@Update("update friend set block=2 where id=#{id} and friendId={friendId}") //즐찾
	int friendFavorite(FriendDTO dto);
	
	//* 즐겨찾기친구해제 */
	@Update("update friend set block=1 where id=#{id} and friendId={friendId}") //즐찾해제
	int friendUnFavorite(FriendDTO dto);
	
	//*친구삭제*/
	@Delete("delete from friend where id=#{id} and friend_id=#{friendId}")
	int delete(FriendDTO dto);
	
	//*친구프로필*/
	@Select("select * from member where id=#{friendId}")
	MemberDTO friendProfile(String friendId);
	
	
	
	
	
		
}
		