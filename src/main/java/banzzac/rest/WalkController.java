package banzzac.rest;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.MemberDTO;
import banzzac.dto.WalkDiaryDTO;
import banzzac.mapper.ReviewMapper;
import banzzac.utill.CommonResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/walk")
public class WalkController {

	@Resource
	ReviewMapper mapper;
	
	/** 산책 다이어리 리스트 가져오기 */
	@GetMapping("diaries")
	public ResponseEntity<CommonResponse<ArrayList<WalkDiaryDTO>>> getWalkingDiary(HttpSession session){
		
		MemberDTO info = (MemberDTO)session.getAttribute("banzzacMember");
		
		return CommonResponse.success(mapper.getWalkList(info));
	}
	
	
	/**약속 잡기 추가*/
	@PostMapping("promise")
	public ResponseEntity<CommonResponse<Integer>> insertPromise(@RequestBody WalkDiaryDTO dto){
		
		return CommonResponse.success(mapper.createPromise(dto));
	}
	
	/**승락 거절*/
	@PostMapping("promise/{promiseStatus}")
	public ResponseEntity<CommonResponse<String>> updatePromiseStatus(HttpSession session,@RequestBody WalkDiaryDTO dto){
		
		if(mapper.updatePromiseStatus(dto) == 0 ) {
			return CommonResponse.error(HttpStatus.BAD_REQUEST, "error - 404.2", "요청에 실패했습니다.");
		}
		
		String msg = "";
		
		if(dto.getPromiseStatus() == 0) {
			msg = "거절 되었습니다";
		}else {
			msg = "산책을 승낙하셨습니다.";
			MemberDTO info =(MemberDTO)session.getAttribute("banzzacMember");
			
			//리뷰 테이블 생성
			mapper.createReviews(dto.getNo(), info.getId(), dto.getMemberId());
		}
		
		return CommonResponse.success(msg);
	}
	
	/** 평가하기 */
	
	
	/** 리뷰 수정 후 내용만 수정*/
	
	/** 리뷰 삭제 */
	
	
	
}
