package banzzac.controll;

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
	
	@GetMapping
	Object list() {
		return mapper.list();
	}

	@GetMapping("/{no}")
	Object detail(ReviewDTO dto) {
		return mapper.delete(dto);
	}
	
	@PostMapping("/{no}/modify")
	Object modify(ReviewDTO dto) {		
		int res = mapper.modify(dto);
		if(res!=0) {
			return mapper.list();
		}
		return "수정 실패";
	}
	
	
	@GetMapping("/{no}/delete")
	Object delete(ReviewDTO dto) {
		System.out.println("들어옴 ? : "+ dto);
		int res = mapper.delete(dto);
		if (res >=1) {
			return mapper.list();
		}
		return "삭제 실패";
	}
	
}
