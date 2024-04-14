package banzzac.controll;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import banzzac.dto.MemberDTO;
import banzzac.dto.PageDTO;
import banzzac.mapper.AdminMapper;
import banzzac.service.MemberManagementService;
import banzzac.service.SalesListService;
import banzzac.service.SalesService;
import banzzac.utill.CommonLayout;
import banzzac.utill.SelectTitle;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/sales/{service}")
public class SalesManagementController {
	
	@Resource
	private PageDTO pageDTO;
	
	@Resource
	private AdminMapper mapper;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@ModelAttribute
	public CommonLayout getCommonData(CommonLayout cl,SelectTitle title) {
		cl.setFolder("sales");
		title.selectTitle(cl.getService());
		return cl;
	}
	
	@ModelAttribute
	public PageDTO getCommonData(@Param("currentPage") PageDTO paging) {
		pageDTO = paging;
		return pageDTO;
	}
	
	/** Service 인터페이스를 상속받아서 Implement를 만들고 그 안에서 비즈니스 로직을 작성하신 후 결과 값을 Return 해주시면 됩니다
	 * */
	//리스트
	@RequestMapping("")
	public String getNewMember(Model mm, MemberDTO dto, @PathVariable String service) {
		
		System.out.println("SalesController 진입");
		
		SalesListService sls = (SalesListService) applicationContext.getBean(service);
		
		mm.addAttribute("mainData",sls.execute(dto));
		
		System.out.println("Sales Controller : "+dto);
		
		return "template";
	}
	
	//상세 조회
	@RequestMapping("{id}")
	public String detailMember(Model mm, MemberDTO dto ,@PathVariable String action) {
		System.out.println("Sales Controller : "+dto);
		
		SalesService ss = (SalesService) applicationContext.getBean(action);
		dto = ss.execute(dto);
		mm.addAttribute("mainData",ss.execute(dto));
		return "template";
	}
	
	
	//수정 삭제
	@RequestMapping("{id}/{action}")
	public String actionMember(Model mm, MemberDTO dto ,@PathVariable String action) {
		System.out.println("Sales Controller : "+dto);
		
		SalesService ss = (SalesService) applicationContext.getBean(action);
		dto = ss.execute(dto);
		mm.addAttribute("mainData",ss.execute(dto));
		return "template";
	}
	
	
}