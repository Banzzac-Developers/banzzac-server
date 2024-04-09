package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {

	/** 최근 리뷰리스트 -> where my_id : session id 와 동일한 것만 select */ 
	@Select("select * from review where my_id='aaa' order by reg_date desc") 
	List<ReviewDTO> list();
}
