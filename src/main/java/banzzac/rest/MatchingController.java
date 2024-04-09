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
		ArrayList<String> test = new ArrayList<>();
		test.add("활발함");
		test.add("소심함");
		test.add("안소심함");
		
		dto.setDogNature(test);
		dto.setDogNatureStr("강남강아지,송파강아지,건대강아지");
		
		return dto;
	}
}
