package banzzac.rest;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.MatchingDTO;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {
	
	
	
	@GetMapping("condition")
	public MatchingDTO getCondition(MatchingDTO dto) {
		
		
		return dto;
	}
}
