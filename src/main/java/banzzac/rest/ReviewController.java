package banzzac.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.ReviewDTO;
import banzzac.mapper.ReviewMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

	@Resource
	ReviewMapper mapper;
	
	/** 최근 산책 목록 */
	@GetMapping
	Object list() {
		return mapper.list("세션아이디");
	}

	/*
	 * @GetMapping("/{no}") Object detail(ReviewDTO dto) { return
	 * mapper.delete(dto); }
	 */
	
	/** 리뷰의 고유 no 를 react에서 받아 수정 -> 수정이 되면 다시 list 를 react로 return */
	@PostMapping("/{no}/modify")
	Object modify(ReviewDTO dto) {		
		int res = mapper.modify(dto);
		if(res!=0) {
			return mapper.list("세션아이디");
		}
		return "수정 실패";
	}
	
	/** 리뷰 삭제*/
	@GetMapping("/{no}/delete")
	Object delete(ReviewDTO dto) {
		System.out.println("들어옴 ? : "+ dto);
		int res = mapper.delete(dto);
		if (res >=1) {
			return mapper.list("세션아이디");
		}
		return "삭제 실패";
	}
	
}
