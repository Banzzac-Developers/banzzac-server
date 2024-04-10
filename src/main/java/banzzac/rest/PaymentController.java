package banzzac.rest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import banzzac.dto.PayApproveDTO;
import banzzac.dto.PayInfoDTO;
import banzzac.mapper.ProfileMapper;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Resource
	ProfileMapper mapper;
	
	private PayApproveDTO payApproveDto;
	
	@PostMapping("/ready")
	public Object getPaymentReady(@RequestBody PayInfoDTO dto) {	
		mapper.payInfoInsert(dto); // react에서 받은 값 db에 저장
		//System.out.println(dto);
		
		RestTemplate restTemplate = new RestTemplate();
		
		// kakao 에 요청할 Header
		HttpHeaders headers = new HttpHeaders(); 
		
		headers.add("Authorization", "SECRET_KEY DEV363D27AC1786201E1E1E880CD565F7F19A499");
		headers.add("Content-Type", "application/json");
		
		// kakao 에 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		System.out.println("1111111111111");
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", dto.getPartner_order_id()+"");
		params.add("partner_user_id", dto.getPartner_user_id());
		params.add("item_name", "매칭권");
		params.add("quantity", dto.getQuantity()+"");
		params.add("total_amount", dto.getTotal_amount()+"");
		params.add("tax_free_amount", "0");
		params.add("approval_url", "http://localhost:5173/profile");
		params.add("cancel_url", "http://localhost:5173/matching");
		params.add("fail_url", "http://localhost:5173/matching");
		System.out.println("222222222222");
		// Header + Body
		HttpEntity<MultiValueMap<String, String>> transform = new HttpEntity<MultiValueMap<String,String>>(params, headers);
		System.out.println(transform);
		try {
			// kakao 에 data 보내기 => PayApproveDTO.class : 응답받을 객체/online/v1/payment/ready
			payApproveDto = restTemplate.postForObject(new URI("https://open-api.kakaopay.com/online/v1/payment/ready"), transform, PayApproveDTO.class);
			System.out.println(payApproveDto.getNext_redirect_pc_url());
			return payApproveDto.getNext_redirect_pc_url();
		} catch (RestClientException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return "tq";
	}
	
	@GetMapping("/success")
    public void kakaoPaySuccess(@RequestParam("pg_token")String pg_token) {
        System.out.println(pg_token);
    }

}
