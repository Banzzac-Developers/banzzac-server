package banzzac.dto;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DashBoardDTO {

	private ArrayList<SalesManagementDTO> salesSummary;
	private Integer todayRegister;
	private Integer reportCount;
	private Integer todayWithdrawnMember;
	private Integer refundCount;
	private Integer reportNo;
	private String memberId;
	private String reportedId;
	private String reportReason;
	private String nickname;
	private String dogName;
}
