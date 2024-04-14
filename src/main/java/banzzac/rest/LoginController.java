package banzzac.rest;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.SessionDTO;
import banzzac.mapper.LoginMapper;
import banzzac.utill.CommonResponse;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Resource
	LoginMapper mapper;
	
	
	//**프론트에서 인가코드받기*/
	@GetMapping("")
	public ResponseEntity<CommonResponse<SessionDTO>> login(@Param("code") String code){
		System.out.println("code : "+code);
		SessionDTO res = new SessionDTO();
		return CommonResponse.success(res);
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
