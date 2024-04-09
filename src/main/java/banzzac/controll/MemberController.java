package banzzac.controll;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.MemberDTO;
import banzzac.mapper.MemberMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/member")
@CrossOrigin("http://localhost:3000/")
public class MemberController {
	
	@Resource
	MemberMapper mapper;

	
	@GetMapping("createMember")
	void createMemberForm() {}
	
	
	@PostMapping("createMember")
	String createMemberReg(MemberDTO dto) {
		mapper.createMember(dto);
		return "login";
	}
	
}
