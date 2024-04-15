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
		RedirectView redirectView = new RedirectView();
		
		KakaoApi kakaoApi = new KakaoApi();
		
		String accessToken = kakaoApi.getAccessToken(code);
		KakaoProfile userInfo = kakaoApi.getUserInfo(accessToken);
		
		// 이걸로 mapper에 진입하여 회원 정보가 있는 지 확인 후에 있다면 그 정보를 session에 저장하고 redirectView에 friends 페이지로 이동 시키게 하면 되고,
		// 만약 없다면 dto에 뽑아온 값들을 담아 redirectView.addStaticAttribute("data", dto); 하여 redirect 시 react에서 받을 수 있게 하시면 됩니다.
		// 나머지는 형님이 잘하실 수 있을 거라 믿어 의심치 않고 맡기겠습니다! 화이팅!
		dto.setId(userInfo.getEmail());
		
		//뽑아올 수 있는 값들
		System.out.println("email : "+ userInfo.getEmail());
		System.out.println("nickname : "+ userInfo.getNickname());
		System.out.println("getGender : "+ userInfo.getGender());
		System.out.println("getPhoneNumber : "+ userInfo.getPhoneNumber());
		System.out.println("getAgeRange : "+ userInfo.getAgeRange());
		
		//로그인 성공 시 보여줄 페이지는 friends 이고, 실패 시 미가입자인 것이니 회원가입페이지로 이동, 비밀번호가 틀렸으면 다시 login Page로 이동시켜주십시오.
		redirectView.setUrl("http://localhost:5173/login");
		return redirectView;
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
