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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import banzzac.dto.MemberDTO;
import banzzac.dto.PageDTO;
import banzzac.dto.ReportDTO;
import banzzac.mapper.AdminMapper;
import banzzac.service.MemberManagementListService;
import banzzac.service.MemberManagementService;
import banzzac.utill.CommonLayout;
import banzzac.utill.SelectTitle;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/management")
public class MemberManagementController {
	
	@Resource
	private PageDTO pageDTO;
	
	@Resource
	private AdminMapper mapper;
	
	@Autowired
	private ApplicationContext applicationContext;
	
//	@ModelAttribute
//	public CommonLayout getCommonData(CommonLayout cl, SelectTitle title) {
//		cl.setFolder("member");
//		title.selectTitle(cl.getService());
//		return cl;
//	}
	
	@ModelAttribute
	public PageDTO getCommonData(@Param("currentPage") PageDTO paging) {
		pageDTO = paging;
		return pageDTO;
	}
	
	/** Service 인터페이스를 상속받아서 Implement를 만들고 그 안에서 비즈니스 로직을 작성하신 후 결과 값을 Return 해주시면 됩니다
	 * */
	//리스트
	@GetMapping("report")
	public String getReportMemberList(Model mm, CommonLayout cl, SelectTitle title) {
		cl.setFolder("report");
		cl.setService("report");
		title.selectTitle("report");
		
		ArrayList<ReportDTO> res = mapper.getReportMemberList(pageDTO);
		
		mm.addAttribute("mainData", res);
		
		return "template";
	}
	
	@GetMapping("reportDetail/{no}")
	public String reportDetail(Model mm, CommonLayout cl, SelectTitle title, @PathVariable int no) {
		cl.setFolder("report");
		cl.setService("reportDetail");
		title.selectTitle("report");
		
		System.out.println("신고하기 상세보기" + no);
		ReportDTO dto = mapper.reportDetail(no);
		mm.addAttribute("report", dto);
		
		return "template";
	}
	
	@PostMapping("reportDetail/modify/{no}")
	public String modifyReportDetail(@ModelAttribute ReportDTO dto, @PathVariable int no) {
		dto.setReportNo(no);
		System.out.println("신고하기 관리자 답변 추가" + dto.getReportNo());
		mapper.modifyReportDetail(dto);
		
		return "redirect:/management/reportDetail/"+dto.getReportNo();
	}
	
	
	@GetMapping("reportDetail/suspend/{id}/{no}")
	public String suspendMember(@PathVariable String id, @PathVariable int no) {
		System.out.println("유저 정지하기" + id);
		
		mapper.suspendMember(id);
		mapper.modifyReportStatus(no);
		
		return "redirect:/management/report";
	}
	
	@GetMapping("suspend")
	public String suspendMemberList(Model mm, CommonLayout cl, SelectTitle title) {
		cl.setFolder("member");
		cl.setService("suspendList");
		title.selectTitle("suspend");
		
		ArrayList<MemberDTO> res = mapper.getSuspendMemberList(pageDTO);
		mm.addAttribute("suspendList", res);
		return "template";
	}
	
	@GetMapping("changeSuspend/{id}")
	public String changeSuspend(@PathVariable String id) {
		System.out.println("유저 정지해제" + id);
		
		mapper.changeSuspendMember(id);
		
		return "redirect:/management/suspend";
	}
	
	@GetMapping("withdrawal")
	public String withdrawalMemberList(Model mm, CommonLayout cl, SelectTitle title) {
		cl.setFolder("member");
		cl.setService("withdrawalList");
		title.selectTitle("withdrawal");
		
		ArrayList<MemberDTO> res = mapper.getWithdrawalMemberList(pageDTO);
		mm.addAttribute("withdrawalList", res);
		return "template";
	}
		
}
