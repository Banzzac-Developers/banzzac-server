package banzzac.controll;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		mm.addAttribute("data",mapper.dailySales());	
		
		return "template";
	}
	
	@GetMapping("weekly")
	public String weeklySales (CommonLayout cl,SelectTitle title, Model mm,@RequestParam(required = false,name =  "year") String yearStr,@RequestParam(required = false,name ="month") String monthStr) {
		cl.setFolder(folder);
		cl.setService("weekly");
		title.selectTitle(cl.getService());
		
		int year, month;
		System.out.println(yearStr+" " + monthStr);
		if(yearStr == null || monthStr == null) {
			year = 2024;
			month = 4;
		}else {
			year = Integer.parseInt(yearStr);
			month = Integer.parseInt(monthStr);
		}
		mm.addAttribute("data",mapper.weeklySales(year,month));

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

		mm.addAttribute("selectedYear",year);
		mm.addAttribute("year",mapper.selectYear());
		mm.addAttribute("data",mapper.montlySales(year));
		
		return "template";
	}
	
	@GetMapping("payment")
	/** 결제 회원 리스트 - 랭킹 위에 3위까지*/
	public String paymentList (CommonLayout cl,SelectTitle title,Model mm,@RequestParam(required = false,name =  "year") String yearStr,@RequestParam(required = false,name ="month") String monthStr) {
		
		int year, month;
		System.out.println(yearStr+" " + monthStr);
		if(yearStr == null || monthStr == null) {
			year = 2024;
			month = 4;
		}else {
			year = Integer.parseInt(yearStr);
			month = Integer.parseInt(monthStr);
			System.out.println(year+" "+month );
		}

		cl.setFolder(folder);
		cl.setService("payment");
		title.selectTitle(cl.getService());
		
		mm.addAttribute("paymentList",mapper.paymentList(year, month));
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
	
	@PostMapping("refund")
	public String  checkRefund(CommonLayout cl,SelectTitle title,@RequestBody SalesManagementDTO dto,Model mm) {
		cl.setFolder(folder);
		cl.setService("refundList");
		title.selectTitle(cl.getService());
		int res = mapper.checkRefund(dto);
		
		System.out.println(dto.getUserId());
		// 환불 상태 변경 완료 + 거절을 클릭할 시에 member테이블의 quantity 다시 더해주기
		if(res>=1 && dto.getRanking()==0) { 
			mapper.plusQuantity(dto);
		} 
		return "template";
	}
	
	
	/** 환불 승인 회원 */
	@GetMapping("refund/approve")
	public String approveRefund (CommonLayout cl,SelectTitle title,Model mm) {
		cl.setFolder(folder);
		cl.setService("approve");
		title.selectTitle(cl.getService());
		// 환불 승인
		mm.addAttribute("data",mapper.refund(1));
		
		return "template";
	}
	
	/** 환불 거절 회원 */
	@GetMapping("refund/refuse")
	public String refuseRefund (CommonLayout cl,SelectTitle title,Model mm) {
		cl.setFolder(folder);
		cl.setService("refuse");
		title.selectTitle(cl.getService());	
		//환불 거절
		mm.addAttribute("data",mapper.refund(0));
		
		return "template";
	}
	
}
