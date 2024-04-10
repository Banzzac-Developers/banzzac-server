package banzzac.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import banzzac.dto.PayApproveDTO;
import banzzac.dto.PayInfoDTO;
import banzzac.mapper.ProfileMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/payment")
public class PayController {

	@Resource
	ProfileMapper mapper;
	
	private PayApproveDTO payApproveDto;
	
	// 결제 요청하기
	 @PostMapping("/ready")
	    public String readyToKakaoPay(@RequestBody PayInfoDTO payInfoDto) {

		 // 결제요청 db 에 저장
		 mapper.payInfoInsert(payInfoDto); 
		 
		 HttpHeaders headers = new HttpHeaders(); 
			// secret key 숨기기
	        headers.set("Authorization", "SECRET_KEY DEV363D27AC1786201E1E1E880CD565F7F19A499");
			headers.set("Content-Type", "application/json"); //jason 형태로 보내기
			
			RestTemplate restTemplate = new RestTemplate();
			
			// kakao 에 요청할 Body
			Map<String, String> params = new HashMap(); //key-value 형태로 저장
			params.put("cid", "TC0ONETIME");
			params.put("partner_order_id", payInfoDto.getPartner_order_id()+"");
			params.put("partner_user_id", payInfoDto.getPartner_user_id());
			params.put("item_name", "매칭권");
			params.put("quantity",payInfoDto.getQuantity()+"");
			params.put("total_amount", payInfoDto.getTotal_amount()+"");
			params.put("tax_free_amount", "0");
			params.put("approval_url", "http://localhost:5173/profile");	//결제 성공 후 보여질 페이지 -> 완료 페이지
			params.put("cancel_url", "http://localhost:5173/matching");		//결제 취소 시 보여질 페이지 -> 주문페이지
			params.put("fail_url", "http://localhost:5173/matching");		//결제 실패 시 보여질 페이지 -> 주문페이지
			
			// Header + Body
			HttpEntity<Map<String, String>> transform = new HttpEntity<>(params, headers);
			
			payApproveDto = restTemplate.postForObject("https://open-api.kakaopay.com/online/v1/payment/ready", transform, PayApproveDTO.class);
			// 결제 요청 후 받은 값 -> payApprove db에 넣는 sql문 작성하기
			
			return payApproveDto.getNext_redirect_pc_url(); //react -> 결제페이지로	redirect
			}
	
}
