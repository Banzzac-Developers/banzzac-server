package banzzac.controll;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.mapper.ReviewMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/review")
@CrossOrigin("http://localhost:3000/")
public class ReviewController {

	@Resource
	ReviewMapper mapper;
	
	@GetMapping
	Object list() {
		return mapper.list();
	}
	
	
}
