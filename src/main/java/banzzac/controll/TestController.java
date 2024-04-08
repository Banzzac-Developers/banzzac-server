package banzzac.controll;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.model.TestPerson;

@RestController
@RequestMapping("/test")
public class TestController {
	
	
	@PostMapping("person")
	public TestPerson postPerson(TestPerson per) {
		System.out.println("person 실행 "+per);
		
		return per;
	}
	
	
}
