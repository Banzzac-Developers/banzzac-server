package banzzac.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class RefundDTO {

	private int partnerOrderId, aprrove;
	private String reason;
	private Date refundRequestDate, approveTime; //환불 신청시간, 환불 승인시간
	private String refundRequestDateStr, approveTimeStr;
	
	public void setRefundRequestDate(Date refundRequestDate) {
		this.refundRequestDate = refundRequestDate;
		this.refundRequestDateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(refundRequestDate);
	}

	public void setRefundRequestDateStr(String refundRequestDateStr) {
		this.refundRequestDateStr = refundRequestDateStr;
		try {
			this.refundRequestDate = new SimpleDateFormat().parse(refundRequestDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
		this.refundRequestDateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(approveTime);
	}

	public void setApproveTimeStr(String approveTimeStr) {
		this.approveTimeStr = approveTimeStr;
		try {
			this.approveTime = new SimpleDateFormat().parse(approveTimeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
