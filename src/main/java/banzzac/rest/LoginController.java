package banzzac.rest;

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
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Resource
	LoginMapper mapper;
	
	
	@GetMapping("oauth2/code/kakao")
	public RedirectView kakaoLogin(@RequestParam String code, HttpSession session, MemberDTO dto) {
		System.out.println("11111111------------");
		RedirectView redirectView = new RedirectView();
		
		KakaoApi kakaoApi = new KakaoApi();
		System.out.println("11111111111111111222222222222");
		String accessToken = kakaoApi.getAccessToken(code);
		System.out.println("accessToken:"+accessToken);
		System.out.println("11111111111111111222222222222333333333333333333");
		KakaoProfile userInfo = kakaoApi.getUserInfo(accessToken);
		
		System.out.println(userInfo);
		
		System.out.println("222222222222-----------------------");
		
		System.out.println(dto);
		
		dto.setId(userInfo.getEmail());
		
		System.out.println(userInfo.getEmail());
		//뽑아올 수 있는 값들
		System.out.println("email : "+ userInfo.getEmail());
		System.out.println("nickname : "+ userInfo.getNickname());
		System.out.println("getGender : "+ userInfo.getGender());
		System.out.println("getPhoneNumber : "+ userInfo.getPhoneNumber());
		
		
		
		
		//로그인 성공 시 보여줄 페이지는 friends 이고, 실패 시 미가입자인 것이니 회원가입페이지로 이동, 비밀번호가 틀렸으면 다시 login Page로 이동시켜주십시오.
		System.out.println("dto.id:"+dto.getId());
		
		
		
		MemberDTO userId = (MemberDTO) mapper.loginId(dto.getId());
		
	    MemberDTO newUserId = new MemberDTO();
	    newUserId.setId(userInfo.getEmail());
	    newUserId.setNickname(userInfo.getNickname());
	    newUserId.setPhone(userInfo.getPhoneNumber());
	    if(userInfo.getGender().equals("male")) {
	    	newUserId.setGender(1);
	    }else {
	    	newUserId.setGender(2);
	    }
	    
	    
	    
	    
	    System.out.println("newUserIdㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ:"+newUserId);
		
		System.out.println("userId:"+userId);
		if(userId==null) {
			session.setAttribute("member", newUserId);
			redirectView.setUrl("http://localhost:5173/createMember");
			redirectView.addStaticAttribute("", userId);
			return redirectView;
		}else {
			session.setAttribute("member", userId);
			redirectView.setUrl("http://localhost:5173/friends");
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
