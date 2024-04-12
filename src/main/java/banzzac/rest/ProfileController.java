package banzzac.rest;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.DogDTO;
import banzzac.dto.MemberDTO;
import banzzac.mapper.DogMapper;
import banzzac.mapper.MemberMapper;
import banzzac.utill.CommonResponse;
import jakarta.annotation.Resource;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {
/** url id : session id로 바꾸기  */
	@Resource
	MemberMapper memMapper;
	
	@Resource
	DogMapper dogMapper;
	
	
	
	@GetMapping("{id}")
	ArrayList<MemberDTO> myProfile(@PathVariable String id){		
		System.out.println(id+"의 프로필");
		
		return memMapper.memberInfo(id);
	}
	
	
	@PostMapping("{id}")
	ArrayList<MemberDTO> myProfileModify(@PathVariable String id, MemberDTO dto) {		
		memMapper.modifyMember(dto);
		ArrayList<MemberDTO> res = memMapper.memberInfo(id);
		System.out.println("modifyMember : " + res);
		
		return res;
	}
	
	
	
	@GetMapping("dog")
	ResponseEntity<CommonResponse<ArrayList<DogDTO>>> listDog(DogDTO dto){
		ArrayList<DogDTO> res = dogMapper.list(dto);
		System.out.println("반려견 전체 리스트"+res);
		return CommonResponse.success(res);
	}

	
	@GetMapping("dog/{id}/{name}")
	ResponseEntity<CommonResponse<DogDTO>> detailDog(@PathVariable String id, @PathVariable String name, DogDTO dto){		
		DogDTO res = dogMapper.dogInfo(dto);
		System.out.println("반려견 상세보기" + res);
		if(res != null) {
			return CommonResponse.success(res);
		}else {
			return CommonResponse.error(HttpStatus.BAD_REQUEST, "Dog Detail Failed", "반려견 정보 없음");
		}		
	}
	
	
	@PostMapping("dog/{id}")
	ResponseEntity<CommonResponse<DogDTO>> addDog(@RequestBody DogDTO dto, @PathVariable String id){
		dto.setId(id);	
		DogDTO res = dogMapper.dogChk(dto);
		
		if(res==null) {
			dogMapper.createDog(dto);
			return CommonResponse.success(dogMapper.dogInfo(dto));	
		}else {
			return CommonResponse.error(HttpStatus.BAD_REQUEST, "Dog Add Failed", "같은 정보 반려견 존재");
		}	
	}

	
	
	@PostMapping("dog/{id}/{name}")
	ResponseEntity<CommonResponse<DogDTO>> modifyDog(@RequestBody DogDTO dto, @PathVariable String id, @PathVariable String name){
		dto.setId(id);
		dto.setName(name);
		System.out.println("반려견 수정");
		if (dogMapper.modify(dto)>=1) {			
			return CommonResponse.success(dogMapper.dogInfo(dto));
		}else {
			return CommonResponse.error(HttpStatus.BAD_REQUEST, "Dog Modify Failed", "반려견 수정 실패");
		}
	}

	@GetMapping("dog/{id}/delete/{name}")
	ResponseEntity<CommonResponse<ArrayList<DogDTO>>> deleteDog(DogDTO dto, @PathVariable String id){
		System.out.println("반려견 삭제" + dto);
		if(dogMapper.delete(dto)>=1) {
			return CommonResponse.success(dogMapper.list(dto));
		}else {
			return CommonResponse.error(HttpStatus.BAD_REQUEST, "Dog Delete Failed", "반려견 삭제 실패");
		}
	}

	
}
