package banzzac.payment;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class PayCancelInfo {

	private Date canceled_at;
	private String status;
	private String partner_order_id;
	private String partner_user_id;
	private int quantity;
	private ArrayList<Integer> approved_cancel_amount;
}
