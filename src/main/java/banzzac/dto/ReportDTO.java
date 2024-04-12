package banzzac.dto;

import java.util.Date;
import org.springframework.stereotype.Component;
import lombok.Data;


@Data
@Component
public class ReportDTO {
	
	private int reportNo;
	private int memberNo;
	private int reportedNo;
	private String reportReason;
	private Date reportTime;
	private int reportStatus;
	private String reportAdminAnswer;
	
	
}
