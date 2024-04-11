package banzzac.payment;

import java.util.Date;

import lombok.Data;

@Data
public class PaySuccessInfo {
	private String aid;
	private String tid;
	private String payment_method_type;
	private Object card_info;
	private Object amount;
	private int quantity;
	private Date approved_at;
}
