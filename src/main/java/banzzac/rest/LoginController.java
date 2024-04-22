package banzzac.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import banzzac.dto.MemberDTO;
import banzzac.mapper.LoginMapper;
import banzzac.utill.KakaoApi;
import banzzac.utill.KakaoProfile;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Resource
	LoginMapper mapper;
	
	
	@GetMapping("oauth2/code/kakao")
	public RedirectView kakaoLogin(@RequestParam String code, HttpSession session, HttpServletResponse  response , MemberDTO dto) {
		
		RedirectView redirectView = new RedirectView();
		
		KakaoApi kakaoApi = new KakaoApi();
		
		String accessToken = kakaoApi.getAccessToken(code);
		System.out.println("accessToken:"+accessToken);
	
		KakaoProfile userInfo = kakaoApi.getUserInfo(accessToken);

		
		System.out.println(dto);
		
		dto.setId(userInfo.getEmail());
		
		System.out.println("dto.id:"+dto.getId());
		
		
		
		MemberDTO userId = (MemberDTO) mapper.loginId(dto.getId());
		MemberDTO newUserId = new MemberDTO();
	    newUserId.setId(userInfo.getEmail());
	    newUserId.setNickname(userInfo.getNickname());
	    newUserId.setPhone("0"+userInfo.getPhoneNumber().substring("+82 ".length()));
	    if(userInfo.getGender().equals("male")) {
	    	newUserId.setGender(1);
	    }else {
	    	newUserId.setGender(2);
	    }
	    
	    
		if(userId==null) {

			System.out.println(" 회원 가입 창으로 ");
			try {
				redirectView.setUrl("http://localhost:5173/signup/user?nickname="+URLEncoder.encode(newUserId.getNickname(), "UTF-8")+"&phone="+newUserId.getPhone()+"&id="+URLEncoder.encode(newUserId.getId(), "UTF-8")+"&gender="+newUserId.getGender());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return redirectView;
		}else {
			System.out.println(" 로그인 성공 후 보여질 화면으로  " + userId);

			
			session.setAttribute("member", userId);
			
			Cookie cookie = new Cookie("JSESSIONID", session.getId());
	        cookie.setPath("/");
	        cookie.setMaxAge(60*60*60);
	        cookie.setHttpOnly(true); // JavaScript에서 쿠키를 읽을 수 없도록 설정
	        response.addCookie(cookie);
	        System.out.println("세션 아이디 : "+session.getId());
	        
			redirectView.setUrl("http://localhost:5173/profile");
			return redirectView;
		}
		
	}
	
	//** 아이디찾기 */
	@GetMapping("searchId")
	void searchIdForm() {
		System.out.println("서치아이디  진입");
	}
	
	
	//** 아이디 찾기 레그*/
	@PostMapping("searchId/{phone}")
	String searchIdReg(@PathVariable String phone) {
		System.out.println("searchId Reg 진입");
		String userId = mapper.searchId(phone);
		if (userId != null) {
			return userId;
		} else {
			return "아이디를 찾을수없습니다.";
		}
	}

	//** 비번찾기*/
	@GetMapping("searchPw")
	void searchPwForm() {
		System.out.println("서치PW  진입");
	}

	//** 비번찾기 레그*/
	@PostMapping("searchPw/{phone}/{id}")
	String searchPwReg(@PathVariable String phone, @PathVariable String id) {
		System.out.println("searchPw Reg 진입");
		String userPw = mapper.searchPw(id, phone);
		if (userPw != null) {
			return userPw;
		} else {
			return "비밀번호를 찾을수없습니다.";
		}
	}

}
