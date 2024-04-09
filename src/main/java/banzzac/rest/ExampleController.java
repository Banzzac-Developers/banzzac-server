package banzzac.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.ChatDTO;

@RestController
@RequestMapping("/api/exam")
public class ExampleController {
	
	
	@PostMapping("send")
	public ChatDTO post(@RequestBody ChatDTO dto) {
		System.out.println("Exam post : "+dto);
		
		
		return dto;
	}
	
	
}
