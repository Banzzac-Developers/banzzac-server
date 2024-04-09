package banzzac.controll;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.DogDTO;
import banzzac.dto.MemberDTO;
import banzzac.mapper.DogMapper;
import banzzac.mapper.MemberMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Resource
	MemberMapper mapper;
	DogMapper domapper;
	
	@GetMapping("createMember")
	void createMemberForm() {
		System.out.println("createMemberForm 진입");
	}
	
	
	@PostMapping("createMember")
	String createMemberReg(MemberDTO dto) {
		mapper.createMember(dto);
		System.out.println("createMemberReg 진입");
		return "login";
	}
	
	
	@GetMapping("createDog")
	void createDogForm() {
		System.out.println("createDogForm 진입");
	}
	
	@PostMapping("createDog")
	String createDogReg(DogDTO dto) {
		domapper.createDog(dto);
		System.out.println("createDogReg 진입");
		return "login";
	}
	
}
