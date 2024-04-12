package banzzac.rest;

import java.util.ArrayList;

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
import jakarta.annotation.Resource;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {
/** url id : session id로 바꾸기  */
	@Resource
	MemberMapper memMapper;
	
	@Resource
	DogMapper dogMapper;
	
	
	
	@GetMapping("/{id}")
	ArrayList<MemberDTO> myProfile(@PathVariable String id){		
		System.out.println(id+"의 프로필");
		
		return memMapper.memberInfo(id);
	}
	
	
	@PostMapping("/{id}")
	ArrayList<MemberDTO> myProfileModify(@PathVariable String id, MemberDTO dto) {		
		memMapper.modifyMember(dto);
		ArrayList<MemberDTO> res = memMapper.memberInfo(id);
		System.out.println("modifyMember : " + res);
		
		return res;
	}
	
	
	
	@GetMapping("/dog/{id}")
	ArrayList<DogDTO> dogList(@PathVariable String id) {		
		ArrayList<DogDTO> res = dogMapper.list(id);
		System.out.println("dogList"+res);
		
		return res;
	}
	
	@GetMapping("/dog/{id}/{name}")
	DogDTO dogInfo(@PathVariable String id, @PathVariable String name) {
		DogDTO dto = dogMapper.dogInfo(id, name);
		System.out.println("dogInfo : " + dto);
		
		return dto;
	}
	
	
	@PostMapping("/dog/{id}")
	DogDTO dogAdd(@RequestBody DogDTO dto, @PathVariable String id) {
		dto.setId(id);	// 아이디, 강아지 이름 같을 경우 add 불가 하도록 추가
		int add = dogMapper.addDog(dto);
		System.out.println("3. 성공 ?"+add);
		
		return dogMapper.dogInfo(id, dto.getName());
	}
	
	
	
	@PostMapping("/dog/{id}/{name}")
	DogDTO modify(DogDTO dto, @PathVariable String id, @PathVariable String name) {
		dogMapper.modify(dto);
		System.out.println(name+" 수정 -> "+dogMapper.dogInfo(id, name));
		
		return dogMapper.dogInfo(id, name);
	}
	
	
	@GetMapping("/dog/{id}/delete/{name}")
	ArrayList<DogDTO> dogDelete(DogDTO dto, @PathVariable String id) {
		dogMapper.delete(dto);
		ArrayList<DogDTO> res = dogMapper.list(id);
		System.out.println("dogDelete : "+res );
		
		return res;
	}
	
}
