package banzzac.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentSuccessDTO {
	
	private long partnerOrderId; // 회원 주문 고유 번호
	private String partnerUserId; // 결제 한 회원
	private String tid; // 결제 고유 번호 ( 승인 = 취소 )
	private String aid; // 결제 고유 번호 ( 승인 != 취소 )
	private String paymentMethodType; // 결제 방법
	private Integer quantity; // 주문 수량
	private int totalAmount; // 주문 금액
	private Date approvedAt; // 결제 승인 시간
	
}
