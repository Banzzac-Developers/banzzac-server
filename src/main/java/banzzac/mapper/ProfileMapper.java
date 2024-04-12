package banzzac.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.MemberDTO;
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
	ArrayList<PaymentSuccessDTO> payList();
	
	
	/** 내가 결제한 리스트 -> 세션아이디 넣기 */
	@Select("select payment_method_type,quantity,total_amount,approved_at from paymentSuccess where partner_user_id=#{partnerUserId} order by approved_at desc")
	ArrayList<PaymentSuccessDTO> myPayList(String partnerUserId);
	
	/** 결제 시 매칭권 갯수 변경
	 * 보유하고 있던 매칭권 수 + 구매한 매칭권 수 */
	@Update("update `member` m "
			+ "join paymentsuccess p"
			+ " on m.id = p.partner_user_id "
			+ "set m.quantity=m.quantity+p.quantity "
			+ "where p.partner_order_id = #{partnerOrderId};")
	int modifyMatchingQuantity(PaymentSuccessDTO dto);
	

	
	/** 결제 요청 시 orderId 생성 -> db에 같은 orderId가 있는 지 확인하고 
	 * 같은 주문번호가 없을 때까지 orderId를 생성하기 위한 sql문 */
	@Select("select partner_order_id from paymentSuccess where partner_order_id=#{partnerOrderId}")
	PaymentSuccessDTO checkOrderId(int partnerOrderId);
	
	
	/** 결제 처음 요청 시 db에 추가 */
	@Insert("insert into paymentSuccess "
			+ "(partner_order_id,partner_user_id,quantity,total_amount) "
			+ "values "
			+ "(#{partnerOrderId},#{partnerUserId},#{quantity},#{totalAmount})")
	int paymentInsert(PaymentSuccessDTO dto);
	
	
	/** 결제 성공 시 db 추가 로 update */
	@Update("update paymentSuccess set "
			+ "tid=#{tid}, aid=#{aid}, approved_at=#{approvedAt}, payment_method_type=#{paymentMethodType}"
			+ "where partner_order_id = #{partnerOrderId}")
	int paySuccess(String tid, String aid, String paymentMethodType, Date approvedAt, int partnerOrderId);
	
	
	/** 카카오에 결제 승인에 필요한 값 select */
	@Select("select partner_order_id,partner_user_id from paymentSuccess where partner_order_id = #{partnerOrderId}")
	PaymentSuccessDTO detail(int partnerOrderId);
	
	
	/** 결제 : 취소/수정 시 db에서 삭제 */
	@Delete("delete from paymentsuccess where partner_order_id= #{partnerOrderId}")
	int delete(PaymentSuccessDTO dto);


}
