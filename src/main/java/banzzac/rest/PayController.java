package banzzac.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import banzzac.dto.PaymentSuccessDTO;
import banzzac.mapper.ProfileMapper;
import banzzac.payment.PayInfoApprove;
import banzzac.payment.PaySuccessInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/payment")
public class PayController {

	@Resource
	ProfileMapper mapper;
	
	private PayInfoApprove payInfoApprove;
	private PaySuccessInfo paySuccessInfo;
	private int orderId;
	
	// kakao 에 결제 준비 요청
	 @PostMapping("/ready")
	 public Object readyToKakaoPay(@RequestBody PaymentSuccessDTO paymentSuccessDto) {
		 	HttpHeaders headers = new HttpHeaders(); 
		 	System.out.println("2111111111111111");
		 	PaymentSuccessDTO dto = mapper.checkOrderId(orderId);
		 	while(true) {	 		
		 		// orderId 만들기		 	
		 		orderId = (int) (Math.random() * Integer.MAX_VALUE);
		 		// db에 같은 값이 없을 때 까지 생성
		 		if(dto.getPartnerOrderId()!=orderId) {
		 			break;
		 		}
		 	}
		 	System.out.println("orderId : "+orderId);
		 	System.out.println("dto orderID : " + dto.getPartnerOrderId());
		 	
		 	
			// secret key 숨기기
	        headers.set("Authorization", "SECRET_KEY DEV363D27AC1786201E1E1E880CD565F7F19A499");
			headers.set("Content-Type", "application/json"); //jason 형태로 보내기
			
			RestTemplate restTemplate = new RestTemplate();
			
			// kakao 에 요청할 Body
			Map<String, String> params = new HashMap(); //key-value 형태로 저장
			params.put("cid", "TC0ONETIME");
			params.put("partner_order_id", orderId+"");
			params.put("partner_user_id", paymentSuccessDto.getPartnerUserId());
			params.put("item_name", "매칭권");
			params.put("quantity",paymentSuccessDto.getQuantity()+"");
			params.put("total_amount", paymentSuccessDto.getTotalAmount()+"");
			params.put("tax_free_amount", "0");
			params.put("approval_url", "http://localhost/api/payment/success/"+orderId+"");	//결제 승인 url
			params.put("cancel_url", "http://localhost/api/payment/cancel"+orderId+"");		//결제 취소 시 보여질 페이지 -> 주문페이지
			params.put("fail_url", "http://localhost/api/payment/fail"+orderId+"");		//결제 실패 시 보여질 페이지 -> 주문페이지
			
			// Header + Body
			HttpEntity<Map<String, String>> transform = new HttpEntity<>(params, headers);
			// kakao 로 준비 요청하기
			payInfoApprove = restTemplate.postForObject("https://open-api.kakaopay.com/online/v1/payment/ready", transform, PayInfoApprove.class);
			
			mapper.paymentInsert(orderId,paymentSuccessDto); 
			
			return payInfoApprove; //react -> 결제페이지로	redirect
		}
	 
	 
	 // 결제 성공 시 pgToken 값 받아오기
	 @GetMapping("/success/{partnerOrderId}")
	 public ResponseEntity<Object> getMethodName(@RequestParam("pg_token") String pgToken, @PathVariable int partnerOrderId) {
		PaymentSuccessDTO dto = mapper.detail(partnerOrderId);
		 
		// kakao 에 요청할 headers
		HttpHeaders headers = new HttpHeaders(); 
		headers.set("Authorization", "SECRET_KEY DEV363D27AC1786201E1E1E880CD565F7F19A499");
		headers.set("Content-Type", "application/json"); //jason 형태로 보내기
			
		RestTemplate restTemplate = new RestTemplate();
			
		// kakao 에 요청할 Body
		Map<String, String> params = new HashMap(); //key-value 형태로 저장
		params.put("cid", "TC0ONETIME");
		params.put("tid", dto.getTid());
		params.put("pg_token",pgToken);
		params.put("partner_order_id", dto.getPartnerOrderId()+"");
		params.put("partner_user_id", dto.getPartnerUserId());

		// Header + Body
		HttpEntity<Map<String, String>> transform = new HttpEntity<>(params, headers);
		
		// kakao 에 결제 승인 요청 + 결제 성공 정보 받아오기
		paySuccessInfo = restTemplate.postForObject("https://open-api.kakaopay.com/online/v1/payment/approve", transform, PaySuccessInfo.class);
		
		// 결제 요청 후 받은 값 -> payApprove db에 update sql문 작성하기
		mapper.paySuccess(paySuccessInfo.getTid(),paySuccessInfo.getAid(),paySuccessInfo.getPayment_method_type(),paySuccessInfo.getApproved_at(),partnerOrderId);
			
		System.out.println("성공 데이터  : "+paySuccessInfo);
		
		// 결제 성공 후 redirect 페이지
		String address = "http://localhost:5173/profile";
		URI uri = URI.create(address);
				
		return ResponseEntity.status(302).location(uri).build();
	 }
	 
	 @GetMapping(path = {"/fail/{partnerOrderId}","/cancel/{partnerOrderId}"})
	 public ResponseEntity<Object> paymentFail(PaymentSuccessDTO dto){
		 
		// db에서 삭제하기
		mapper.delete(dto);

		// 결제 실패, 취소 후 redirect 페이지
		String address = "http://localhost:5173";
		URI uri = URI.create(address);
		System.out.println(address);
		return ResponseEntity.status(302).location(uri).build();
	 }
	 
	 /** 결제 내역 보기 */
	 //@GetMapping("/paies")
	 @GetMapping("/{partnerUserId}")
	 public ArrayList<PaymentSuccessDTO> paymentHistory(@PathVariable String partnerUserId){
		 System.out.println();
		 return mapper.myPayList(partnerUserId);
	 }
	 
	
}
