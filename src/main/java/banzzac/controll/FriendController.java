package banzzac.controll;


import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.FriendDTO;
import banzzac.mapper.FriendMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

	
	@Resource
	FriendMapper mapper;
	
	@GetMapping("list/{id}")
	public List<FriendDTO> friendList(@PathVariable String id) {
		List<FriendDTO> res = mapper.list(id);
		return res;
	}
	

	@GetMapping("{id}/delete/{friendId}")
	Object delete(FriendDTO dto,@PathVariable String id,@PathVariable String friendId) {
		
		mapper.delete(dto);
		return mapper.list(id);
	}
	
	
	
	
	
	
}
