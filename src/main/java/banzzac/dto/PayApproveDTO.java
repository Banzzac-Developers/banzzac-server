package banzzac.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PayApproveDTO {

	private String payment_method_type,  tid, next_redirect_pc_url;
	private int partner_order_id;
	//private Date approved_at;
	//aid,

}
