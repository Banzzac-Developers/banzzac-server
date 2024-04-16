package banzzac.controll;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import banzzac.dto.PageDTO;
import banzzac.dto.SalesManagementDTO;
import banzzac.mapper.AdminMapper;
import banzzac.utill.CommonLayout;
import banzzac.utill.SelectTitle;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/sales")
public class SalesManagementController {
	private final String folder = "sales";
	@Resource
	private PageDTO pageDTO;
	
	@Resource
	private AdminMapper mapper;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@ModelAttribute
	public PageDTO getCommonData(@Param("currentPage") PageDTO paging) {
		pageDTO = paging;
		return pageDTO;
	}
	
	@GetMapping("daily")
	public String dailySales(CommonLayout cl,SelectTitle title,Model mm, SalesManagementDTO dto) {
		cl.setFolder(folder);
		cl.setService("daily");
		title.selectTitle(cl.getService());
		
		//dto.daily();
		
		mm.addAttribute("day",dto.getDailyRange());
		mm.addAttribute("data",mapper.dailySales());	
		//System.out.println(mapper.dailySales());
		
		return "template";
	}
	
	@GetMapping("weekly")
	public String weeklySales (CommonLayout cl,SelectTitle title) {
		cl.setFolder(folder);
		cl.setService("weekly");
		title.selectTitle(cl.getService());
		
		return "template";
	}
	
	@GetMapping("monthly")
	public String monthlySales (CommonLayout cl,SelectTitle title, Model mm) {
		cl.setFolder(folder);
		cl.setService("monthly");
		title.selectTitle(cl.getService());
		
		ArrayList<SalesManagementDTO> res = mapper.montlySales();
		mm.addAttribute("data",res);
		System.out.println(res);
		
		return "template";
	}
	
}
