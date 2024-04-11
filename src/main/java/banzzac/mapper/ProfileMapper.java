package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.DogDTO;
import banzzac.dto.MemberDTO;
import banzzac.dto.PayApproveDTO;
import banzzac.dto.PayInfoDTO;

@Mapper
public interface ProfileMapper {
	
	
	/*
	 최근 산책 목록
	 # select * from 약속테이블 
	 where end_walk_time < sysdate() && my_id=sessionID || your_id=sessionID order by start_walk_titme desc
	 */
	
	/**/
	
	/** 결제 승인 된 회원 리스트 */
	@Select("select * from paymentApprove")
	List<PayApproveDTO> payList();
	
	@Insert("insert into paymentRequest "
			+ "(partner_order_id,partner_user_id,quantity,total_amount) "
			+ "values "
			+ "(#{partner_order_id},#{partner_user_id},#{quantity},#{total_amount})")
	int payInfoInsert(PayInfoDTO dto);

}
