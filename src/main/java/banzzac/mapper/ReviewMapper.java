package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {


	@Select("select * from review where my_id = #{id} order by reg_date desc") 
	List<ReviewDTO> list(String id);
	
	/** 리뷰 상세보기 */
	
	/** 
	 수정 필요
	 약속 테이블의 end_walk_time < sysdate() 찾기 -> my_id, your_id, no을 받아오기 -> no 당 my_id <-> your_id , 2개 insert 가능 -> 기본키로
	 */
	@Insert("insert into review (my_id, your_id,review_score,content,reg_date) values (#{myId}, #{yourID}, #{reviewScore}, #{content}, sysdate())")
	int insert(ReviewDTO dto);
	
	/** 리뷰 수정 후 내용만 수정 */
	@Update("update review set review_score=#{reviewScore}, content=#{content} where no = #{no}")
	int modify(ReviewDTO dto);
	
	/** 리뷰 삭제 */
	@Delete("delete from review where no=#{no}")
	int delete(ReviewDTO dto);

}
