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
	public String monthlySales (CommonLayout cl,SelectTitle title, Model mm, @Param("year") Integer year) {
		if(year == null) {
			year = 2024;
		}
		cl.setFolder(folder);
		cl.setService("monthly");
		title.selectTitle(cl.getService());
		

		ArrayList<SalesManagementDTO> res = mapper.montlySales(year);
		mm.addAttribute("selectedYear",year);
		mm.addAttribute("year",mapper.selectYear());
		mm.addAttribute("data",res);
		
		return "template";
	}
	
	@GetMapping("payment")
	/** 결제 회원 리스트 - 랭킹 위에 3위까지*/
	public String paymentList (CommonLayout cl,SelectTitle title,Model mm,@Param("year") Integer year,@Param("month") Integer month) {
		cl.setFolder(folder);
		cl.setService("paymentList");
		title.selectTitle(cl.getService());
		
		
		mm.addAttribute("userRanking",mapper.ranking(year, month));
		
		return "template";
	}
	

	/** 환불 신청 회원 */
	@GetMapping("refund")
	public String refundList (CommonLayout cl,SelectTitle title,Model mm) {
		cl.setFolder(folder);
		cl.setService("refundList");
		title.selectTitle(cl.getService());
		// 환불 대기상태
		mm.addAttribute("data",mapper.refund(2));
	
		return "template";
	}
	
	/** 환불 승인 회원 */
	@GetMapping("refund/approve")
	public String refundApprove (CommonLayout cl,SelectTitle title,Model mm) {
		cl.setFolder(folder);
		cl.setService("approve");
		title.selectTitle(cl.getService());
		// 환불 승인
		mm.addAttribute("data",mapper.refund(1));
		
		return "template";
	}
	
	/** 환불 거절 회원 */
	@GetMapping("refund/refuse")
	public String refundRefuse (CommonLayout cl,SelectTitle title,Model mm) {
		cl.setFolder(folder);
		cl.setService("refuse");
		title.selectTitle(cl.getService());	
		//환불 거절
		mm.addAttribute("data",mapper.refund(0));
		
		return "template";
	}
	
}
