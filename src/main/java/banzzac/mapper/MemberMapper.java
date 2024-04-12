package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.DogDTO;
import banzzac.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	@Insert("insert into member"
			+"(id,pwd,gender,age,date,img,walking_style,nickname)values "
			+"(#{id},#{pwd},#{gender},#{age},sysdate(),#{img},#{walkingStyleStr},#{nickname})")
	int createMember(MemberDTO dto);

	
	/** 모든 회원 리스트 */
	@Select("select * from member")
	List<MemberDTO> memberList();
	
	/** 로그인한 회원의 정보 불러오기 */
	@Select("select * from member where id = #{id}")
	@Result(property = "walkingStyleStr",column = "walking_style" )
	List<MemberDTO> memberInfo(String id);
	
	/** 회원 개인정보 수정 */
	@Update("update `member` set pwd=#{pwd}, age=#{age}, img=#{img} ,walking_style=#{walkingStyleStr}, nickname=#{nickname}")
	int ModifyMember(MemberDTO dto);
	
	/** 결제하면 수량 늘어남, 사용하면 줄어듦 */
	int ModifyMatchingQuantity();
	
	/** 리뷰 받은 후 온도, 리뷰 받은 수 변경 
	  Temperature = Temperature + review_score*20 / cnt = cnt+review insert 갯수
	 */
	//@Update("update `member` set Temperature=#{Temperature} , cnt=#{cnt} where member table의 id = review table의 your_id일 경우")
	int ModifyOndo(MemberDTO dto);
	
	/** 회원탈퇴 시 권한 0으로 변경 */
	@Update("update `member` set isGrant = 0 where id = #{id}")
	int withdrawMember(MemberDTO dto);

}
		