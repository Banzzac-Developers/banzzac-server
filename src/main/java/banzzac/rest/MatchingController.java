package banzzac.rest;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ArrayList<MatchingDTO>> getMatchedMembers(@RequestBody MatchingDTO dto) {
		
		//디비에서 뽑아온 값
		ArrayList<MatchingDTO> dtos = mapper.searchMatchingMembers(dto);
		
		
		return ResponseEntity.ok(dtos);
	}
}
