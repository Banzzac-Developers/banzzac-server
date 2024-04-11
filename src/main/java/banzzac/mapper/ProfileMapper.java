package banzzac.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.PaymentSuccessDTO;

@Mapper
public interface ProfileMapper {
	
	
	/*
	 최근 산책 목록
	 # select * from 약속테이블 
	 where end_walk_time < sysdate() && my_id=sessionID || your_id=sessionID order by start_walk_titme desc
	 */
	
	/**/
	
	/** 결제 완료 한 모든 회원 리스트 */
	@Select("select * from paymentSuccess")
	List<PaymentSuccessDTO> payList();
	
	/** 내가 결제한 리스트 -> 세션아이디 넣기 */
	@Select("select partner_order_id,payment_method_type,quantity,total_amount,approved_at from paymentSuccess where partner_user_id=#{partnerUserId} order by approved_at desc")
	List<PaymentSuccessDTO> myPayList(String partnerUserId);
	
	@Insert("insert into paymentSuccess "
			+ "(partner_order_id,partner_user_id,quantity,total_amount) "
			+ "values "
			+ "(#{partnerOrderId},#{partnerUserId},#{quantity},#{totalAmount})")
	int paymentInsert(PaymentSuccessDTO dto);
	
	/** 결제 성공 시 db 추가 할 내용 */
	@Update("update paymentSuccess set "
			+ "tid=#{tid}, aid=#{aid}, approved_at=#{approvedAt}, payment_method_type=#{paymentMethodType}"
			+ "where partner_order_id = #{partnerOrderId}")
	int paySuccess(String tid, String aid, String paymentMethodType, Date approvedAt, long partnerOrderId);
	
	/** 카카오에 결제 승인에 필요한 값 select */
	@Select("select partner_order_id,partner_user_id from paymentSuccess where partner_order_id = #{partnerOrderId}")
	PaymentSuccessDTO detail(long partnerOrderId);
	
	/** 결제 : 취소/수정 시 db에서 삭제 */
	@Delete("delete from paymentsuccess where partner_order_id= #{partnerOrderId}")
	int delete(long partnerOrderId);


}
