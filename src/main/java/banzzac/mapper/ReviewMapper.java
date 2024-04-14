package banzzac.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.MemberDTO;
import banzzac.dto.ReviewDTO;
import banzzac.dto.WalkDiaryDTO;

@Mapper
public interface ReviewMapper {


	@Select("select * from review where my_id = #{id} order by reg_date desc") 
	List<ReviewDTO> list(String id);
	
	/** 리뷰 상세보기 */
	
	/** 
	 수정 필요
	 약속 테이블의 end_walk_time < sysdate() 찾기 -> my_id, your_id, no을 받아오기 -> no 당 my_id <-> your_id , 2개 insert 가능 -> 기본키로
	 */
	
	
	
	
	/** 리뷰 수정 후 내용만 수정 */
	@Update("update review set review_score=#{reviewScore}, content=#{content} where no = #{no}")
	int modify(ReviewDTO dto);
	
	
	
	/** 리뷰 삭제 */
	@Delete("delete from review where no=#{no}")
	int delete(ReviewDTO dto);
	
	
	
	/** 약속 잡기 추가 */
	@Insert("insert into promise "
			+ " (my_id, your_id, start_walk_time, end_walk_time,Comment)"
			+ " values"
			+ " (#{sessionId}, #{memberId}, #{startWalkTime}, #{endWalkTime})")
	public int createPromise(WalkDiaryDTO dto);
	
	
	
	/**
	 * 산책 다이어리 목록 <리스트>
	 * 내 아이디랑 같으며
	 * 약속 잡기의 산책 끝시간이 지난 것들을 모두 뽑아옴
	 * 화면 표시 내역은
	 * 상대방 아이디, 강아지 한마리 , 산책 일자, 내가 평가한 점수가 떠야함
	 * 가져 가야할 것은 + 약속 번호
	 * */
	 @Select({
	        " SELECT",
		        " p.no,",
		        " p.start_walk_time,",
		        " p.end_walk_time,",
		        " p.comment,",
		        " p.promise_status,",
		        " m.nickname AS memberNickname,",
		        " m.img AS memberImg,",
		        " m.id AS memberId,",
		        " m.no AS memberNo,",
		        " r.review_score as review_score",
		        " r.content as review_content",
		        " MIN(d.name) AS dogName,",
		        " MIN(d.img) AS dogImg",
	        " FROM",
	        	" promise p",
	        " LEFT JOIN member m",
	        " ON p.your_id = m.id",
	        " LEFT JOIN dog d ",
	        " ON p.your_id = d.id",
	        " LEFT JOIN review r",
	        " ON p.no = r.no",
	        " WHERE",
	        	" p.my_id = #{id}",
	        " AND",
	        	" p.end_walk_time < NOW()",
	        " AND",
	       		" p.no IN (SELECT MIN(no) FROM promise WHERE my_id = #{id} AND end_walk_time < NOW() GROUP BY no)",
	        " GROUP BY",
		        " p.no,",
		        " p.start_walk_time,",
		        " p.end_walk_time,",
		        " p.comment,",
		        " p.promise_status,",
		        " m.nickname,",
		        " m.img,",
		        " m.id,",
		        " m.no"
	    })
	public ArrayList<WalkDiaryDTO> getWalkList(MemberDTO dto);
	
	/** 
	 * 산책 약속잡기 승낙 / 거절
	 * 약속 번호와 승낙 대상의 아이디가 같을 때,
	 * promise_status 가 2로 업데이트 된다.
	 * 0은 거절, 1은 승낙대기 
	 * */
	 @Update("update promise set"
	 		+ " promise_status = #{promiseStatus}"
	 		+ " where your_id = #{memberId} ")
	 public int updatePromiseStatus(WalkDiaryDTO dto);
	 
	 
	/**
	 * 약속 잡기를 하고 승낙하면 실행 될 것.
	 * 동일한 약속 번호가 두 개가 생기며 , 내 아이디, 상대 아이디, 평점, 리뷰내용, 등록일, 평점 각각 등록
	 * */
	@Insert("insert into reivew (no, my_id, your_id)"
			+ " values"
			+ " (#{no},#{myId},#{yourId}),"
			+ " (#{no},#{yourId},#{myId})")
	public int createReviews(int no, String myId, String yourId);
	
	
	/** 평가하기 */
	@Update("update review set"
			+ " review_score = #{reviewScore}, content = #{content}, reg_date = sysdate()"
			+ " where my_id = #{myId} and no = #{no}")
	public int registerReview(ReviewDTO dto);
	
	
	
	
	
	
}
