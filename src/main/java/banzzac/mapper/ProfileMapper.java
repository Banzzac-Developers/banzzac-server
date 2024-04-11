package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import banzzac.dto.PaymentSuccessDTO;

@Mapper
public interface ProfileMapper {
	
	
	/*
	 최근 산책 목록
	 # select * from 약속테이블 
	 where end_walk_time < sysdate() && my_id=sessionID || your_id=sessionID order by start_walk_titme desc
	 */
	
	/**/
	
	/** 결제 완료 한 회원 리스트 */
	@Select("select * from paymentSuccess")
	List<PaymentSuccessDTO> payList();
	
	@Insert("insert into paymentSuccess "
			+ "(partner_order_id,partner_user_id,quantity,total_amount) "
			+ "values "
			+ "(#{partnerOrderId},#{partnerUserId},#{quantity},#{totalAmount})")
	int paymentInsert(PaymentSuccessDTO dto);
	
	@Select("select partner_order_id,partner_user_id from paymentSuccess where partner_order_id = #{partnerOrderId}")
	PaymentSuccessDTO detail(long partnerOrderId);
	

}
