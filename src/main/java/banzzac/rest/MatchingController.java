package banzzac.rest;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.MatchingDTO;
import banzzac.mapper.MatchingMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {
	
	@Resource
	private MatchingMapper mapper;
	
	@GetMapping("condition")
	public MatchingDTO getCondition(MatchingDTO dto) {
		
		
		return dto;
	}
	
	@PostMapping("members")
	public ArrayList<MatchingDTO> getMatchedMembers(@RequestBody MatchingDTO dto) {
		System.out.println("dto 값 ===>\n\n\n"+dto+"\n\n");
		ArrayList<MatchingDTO> dtos = mapper.searchMatchingMembers(dto);
		System.out.println("dtos 값 ===>\n\n\n"+dtos+"\n\n");
		return dtos;
	}
}
