package banzzac.controll;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import banzzac.dto.PageDTO;
import banzzac.mapper.AdminMapper;
import banzzac.utill.CommonLayout;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/admin/{folder}/{service}")
public class AdminController {
	
	/** 페이징 사용 법 ==> 
	 * 1. 페이징이 필요한 메소드 매개변수에 PageDTO 선언 
	 * 2. @PathVariable을 쓰든, param을 쓰든 이름을 무조건 currentPage로 작성 
	 * 3. 해당 테이블의 총 갯수를 구해오는 매퍼 작성
	 * 4. pageDTO.setTotalPage에 셋팅
	 * 5. HTML 원하는 부위에 
	 <div th:replace="~{inc/paging(${pageDTO.currentPage},
	  	${pageDTO.prevBlock}, ${pageDTO.nextBlock},
	   	${pageDTO.minPage}, ${pageDTO.maxPage},
	    ${pageDTO.isNextBtn} )}"></div> 
	   	추가 하면 페이징 적용 완료*/
	@Resource
	private PageDTO pageDTO;
	@Resource
	private AdminMapper mapper;
	
	
	@ModelAttribute
	public CommonLayout getCommonData(CommonLayout cl) {
		System.out.println("cl : "+ cl);
		return cl;
	}
	
	
	@RequestMapping("/{currentPage}")
	public String goToTemplate(PageDTO paging, Model model) {
		paging.setTotalPage(mapper.getTotalReportCount());
		model.addAttribute("data", mapper.getReportMemberList(paging));
		System.out.println("어떻게 된 거지  : " + paging);
		return "template";
	}
	
	
}
