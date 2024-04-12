package banzzac.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import banzzac.utill.CommonLayout;

@Controller
@RequestMapping("/admin/{folder}/{service}")
public class AdminController {
	
	
	@ModelAttribute
	public CommonLayout getCommonData(CommonLayout cl) {
		System.out.println("cl : "+ cl);
		return cl;
	}
	
	
	@RequestMapping("")
	public String goToTemplate() {
		
		return "template";
	}
	
	
}
