package banzzac.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.DogDTO;
import banzzac.dto.MemberDTO;
import banzzac.dto.ReportDTO;
import banzzac.mapper.DogMapper;
import banzzac.mapper.MemberMapper;
import banzzac.utill.CommonResponse;
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
	ResponseEntity<CommonResponse<DogDTO>> createDogReg(DogDTO dto) {
		domapper.createDog(dto);
		System.out.println("createDogReg 진입");
		return CommonResponse.success(dto);
	}
	
	
	
	/**
	 * 멤버 신고하는 기능
	 * */
	@PostMapping("/report")
	public ResponseEntity<CommonResponse<ReportDTO>> reportMember(@RequestBody ReportDTO dto){
		System.out.println("dto : " + dto);
		if(mapper.reportMember(dto) >= 1 ) {
			return CommonResponse.success(dto) ;
		}else {
			return CommonResponse.error(HttpStatus.NOT_FOUND, "Not found Member", "신고 대상이 없습니다.");
		}
	}
	
}
