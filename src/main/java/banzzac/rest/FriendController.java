package banzzac.rest;


import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.FriendDTO;
import banzzac.dto.MemberDTO;
import banzzac.mapper.FriendMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

	
	@Resource
	FriendMapper mapper;
	
	/** 친구리스트*/
	@GetMapping("list/{id}")
	public List<FriendDTO> friendList(@PathVariable String id) {
		List<FriendDTO> res = mapper.list(id);
		return res;
	}
	
	/** 차단친구리스트*/
	@GetMapping("blockList/{id}")
	public List<FriendDTO> friendblockList(@PathVariable String id) {
		List<FriendDTO> res = mapper.blockList(id);
		return res;
	}
	
	/** 즐겨찾기 친구리스트*/
	@GetMapping("favoriteList/{id}")
	public List<FriendDTO> favoriteList(@PathVariable String id) {
		List<FriendDTO> res = mapper.favoriteList(id);
		return res;
	}
	
	/** 친구 삭제  */
	@GetMapping("{id}/delete/{friendId}")
	Object delete(FriendDTO dto,@PathVariable String id,@PathVariable String friendId) {
		
		mapper.delete(dto);
		return mapper.list(id);
	}
	
	//** 친구프로필 */
	@GetMapping("friendProfile/{friendId}")
	public MemberDTO friendProfile(@PathVariable String friendId){
		MemberDTO res = mapper.friendProfile(friendId);
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
}
