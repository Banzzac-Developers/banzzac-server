package banzzac.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.mapper.DogMapper;
import banzzac.mapper.MemberMapper;
import jakarta.annotation.Resource;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Resource
	MemberMapper memMapper;
	
	@Resource
	DogMapper dogMapper;
	
	/*
	 @GetMapping("/member") 
	 Object memList() { 
	 	return memMapper.list(); 
	 }
	 */
	
	@GetMapping("/dog")
	Object dogList() {
		return dogMapper.list("세션아이디");
	}
	
}
