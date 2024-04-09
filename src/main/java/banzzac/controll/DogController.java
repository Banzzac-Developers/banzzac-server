package banzzac.controll;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.mapper.DogMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/dog")
@CrossOrigin("http://localhost:3000/")
public class DogController {
	
	@Resource
	DogMapper mapper;

	
	@GetMapping("createDog")
	void createDogForm() {}
	
	@PostMapping("createDog")
	String createDogReg() {
		return "login";
	}
}
