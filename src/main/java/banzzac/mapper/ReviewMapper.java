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
	
	/** 
	 수정 필요
	 산책 완료! -> 약속 잡기의 my_id, your_id을 받아오기 -> review 의 my_id, your_id에 insert
	 */
	@Insert("insert into review (my_id, your_id,review_score,content,reg_date) values (#{myId}, #{yourID}, #{reviewScore}, #{content}, sysdate())")
	int insert(ReviewDTO dto);
	
	/** 리뷰 수정 후 내용만 수정 */
	@Update("update review set review_score=#{reviewScore}, content=#{content} where no = #{no}")
	int modify(ReviewDTO dto);
	
	/** 리뷰 삭제 */
	@Delete("delete from review where no=#{no}")
	int delete(ReviewDTO dto);
	
	/* 
	 온도 계산 
	 
	 1. review , member table join / where your_id = id
	 2. select reviewScore review table 
	 3. reviewScore*20 / cnt +1 => member table Temperature / cnt 에 insert	  
	 */
}
