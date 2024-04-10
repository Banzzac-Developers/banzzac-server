package banzzac.dto;

import lombok.Data;

@Data
public class PayInfoDTO {

	private String partner_user_id; // 결제 한 유저
	private String status; // 결제 상태
	private int quantity; // 결제 수량
	private int total_amount; // 결제 총 금액
	private long partner_order_id; // 주문번호
}
