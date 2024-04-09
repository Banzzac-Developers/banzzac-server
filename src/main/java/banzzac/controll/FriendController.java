package banzzac.controll;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.mapper.FriendMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/dog")
@CrossOrigin("http://localhost:3000/")
public class FriendController {

	
	@Resource
	FriendMapper mapper;
	
}
