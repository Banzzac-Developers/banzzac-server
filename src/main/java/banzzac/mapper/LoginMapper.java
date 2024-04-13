package banzzac.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.MemberDTO;

@Mapper
public interface LoginMapper {

	//**아이디찾기 */
	@Select("select id from member where phone=#{phone}")
	String searchId(String phone);
	//**비밀번호찾기 */
	@Select("select pwd from member where id=#{id} and phone=#{phone}")
	String searchPw(String phone,String id);
	
	

}
		