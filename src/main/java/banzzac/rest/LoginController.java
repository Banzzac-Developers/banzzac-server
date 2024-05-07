package banzzac.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import banzzac.dto.MemberDTO;
import banzzac.jwt.JwtToken;
import banzzac.jwt.JwtTokenProvider;
import banzzac.jwt.LoginDTO;
import banzzac.jwt.LoginServiceImpl;
import banzzac.jwt.MemberDetail;
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
	
	@Resource
	LoginServiceImpl loginServiceImpl;
	
	@Resource
	JwtTokenProvider jwtTokenProvider;
	
	
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
	
	
	/** 직접 로그인
	 * checkLogin으로 먼저 해당 아이디와 비밀번호가 있는지 확인 한 후에 없으면 Excaption으로 해당 값이 없다는 것을 확인
	 * 있다면 진행,
	 *  loadUserByUsername 으로 authentication 값을 반환 받고 그 값으러
	 *  JWT 토큰, 재발급 토큰을 만든 후에 Front에 넘겨준다.*/
	@PostMapping("")
	public ResponseEntity<?> login(@RequestBody LoginDTO info) {
		try {
			loginServiceImpl.checkLoin(info.getId(), info.getPwd());
			UserDetails userDetails  = loginServiceImpl.loadUserByUsername(info.getId());
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
			
			System.out.println("JWT 토큰 생성 후 발급 : "+ jwtToken);
			return ResponseEntity.ok(jwtToken);
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
	

}
