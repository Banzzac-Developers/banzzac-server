package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {

	/** 최근 리뷰리스트 -> where my_id : session id 의 list select 
	 => 약속잡기의 my_id 와 your_id 가 같아야함
	 */ 
	@Select("select * from review where my_id='aaa' order by reg_date desc") 
	List<ReviewDTO> list();
	
	/** 리뷰 수정 */
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
