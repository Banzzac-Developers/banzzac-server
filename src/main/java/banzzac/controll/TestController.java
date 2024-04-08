package banzzac.controll;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.model.TestPerson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping("person")
	public TestPerson postPerson(TestPerson per) {
		log.debug("person 실행 = {}",per);
		return per;
	}
	
	
}
