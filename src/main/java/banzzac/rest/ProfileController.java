package banzzac.rest;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.DogDTO;
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
	
	/*
	 @GetMapping("/member") 
	 Object memList() { 
	 	return memMapper.list(); 
	 }
	 */
	
	/** 내 강아지 전체 목록 */
	@GetMapping("/dog/{id}")
	ArrayList<DogDTO> dogList(@PathVariable String id) {
		return dogMapper.list(id);
	}
	
	/** 내 강아지 각각 정보 조회 */
	@GetMapping("/dog/{id}/{name}")
	DogDTO detail(@PathVariable String id, @PathVariable String name) {
		System.out.println("내 강아지 상세 : "+name);
		return dogMapper.detail(id, name);
	}
	
	/** 내 강아지 각각 수정 -> 변경 된 내용 return */
	@PostMapping("/dog/{id}/{name}")
	DogDTO modify(DogDTO dto, @PathVariable String id, @PathVariable String name) {
		dogMapper.modify(dto);
		System.out.println(name+" 수정 -> "+dogMapper.detail(id, name));
		
		return dogMapper.detail(id, name);
	}
	
	
	/** 내 강아지 삭제 -> 변경 된 리스트 return */
	@GetMapping("/dog/{id}/delete/{name}")
	ArrayList<DogDTO> dogDelete(DogDTO dto, @PathVariable String id) {

		dogMapper.delete(dto);
		
		return dogMapper.list(id);
	}
	
}
