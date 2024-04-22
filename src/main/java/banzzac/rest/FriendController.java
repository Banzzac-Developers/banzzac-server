package banzzac.rest;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.FriendDTO;
import banzzac.dto.MemberDTO;
import banzzac.mapper.FriendMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

	
	@Resource
	FriendMapper mapper;
	
	/** 친구리스트*/
	@GetMapping("list/{id}")
	public List<FriendDTO> friendList(@PathVariable String id,HttpSession session) {

		MemberDTO dto = (MemberDTO)session.getAttribute("member");
		List<FriendDTO> res = mapper.list(dto.getId());
		return res;
	}
	
	/** 차단친구리스트*/
	@GetMapping("blockList/{id}")
	public List<FriendDTO> friendblockList(@PathVariable String id,HttpSession session) {
		MemberDTO dto = (MemberDTO)session.getAttribute("member");
		List<FriendDTO> res = mapper.blockList(dto.getId());
		return res;
	}
	
	/** 즐겨찾기 친구리스트*/
	@GetMapping("favoriteList/{id}")
	public List<FriendDTO> favoriteList(@PathVariable String id,HttpSession session) {
		MemberDTO dto = (MemberDTO)session.getAttribute("member");
		List<FriendDTO> res = mapper.favoriteList(dto.getId());
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
	
	/** 친구차단  */
	@GetMapping("{id}/friendBlock/{friendId}")
	Object friendBlock(FriendDTO dto,@PathVariable String id,@PathVariable String friendId) {
		System.out.println("왔냐"+dto);
		mapper.friendBlock(dto);
		return mapper.list(id);
	}
	
	/** 친구차단해제  */
	@GetMapping("{id}/friendUnBlock/{friendId}")
	Object friendUnBlock(FriendDTO dto,@PathVariable String id,@PathVariable String friendId) {
		System.out.println("왔냐"+dto);
		mapper.friendUnBlock(dto);
		return mapper.blockList(id);
	}
	
	/** 친구즐겨찾기 추가  */
	@GetMapping("{id}/friendFavorite/{friendId}")
	Object friendFavorite(FriendDTO dto,@PathVariable String id,@PathVariable String friendId) {
		System.out.println("왔냐"+dto);
		mapper.friendFavorite(dto);
		System.out.println(id);
		return mapper.list(id);
	}
	
	/** 친구즐겨찾기 해제  */
	@GetMapping("{id}/friendUnFavorite/{friendId}")
	Object friendUnFavorite(FriendDTO dto,@PathVariable String id,@PathVariable String friendId) {
		System.out.println("왔냐"+dto);
		mapper.friendUnFavorite(dto);
		return mapper.list(id);
	}
	
	
	
	
	
	
	
	
	
	
	
}
