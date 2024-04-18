package banzzac.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.MatchingDTO;
import banzzac.dto.MemberDTO;
import banzzac.mapper.MatchingMapper;
import banzzac.utill.CommonResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {
	
	@Resource
	private MatchingMapper mapper;
	
	@GetMapping("condition")
	public MatchingDTO getCondition(HttpSession session, MatchingDTO dto) {
		MemberDTO info = (MemberDTO)session.getAttribute("member");
		dto.setNo(1);
		return mapper.showMatchingCondition(dto);
	}
	
	
	@PostMapping("members")
	public ResponseEntity<CommonResponse<ArrayList<MatchingDTO>>> getMatchedMembers(@RequestBody MatchingDTO dto) {
		
		//디비에서 뽑아온 값
		ArrayList<MatchingDTO> dtos = mapper.searchMatchingMembers(dto);
		
		
		return CommonResponse.success(dtos);
	}
}
