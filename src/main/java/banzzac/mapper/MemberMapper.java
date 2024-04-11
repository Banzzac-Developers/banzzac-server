package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	
	@Insert("insert into member"
			+"(id,pwd,gender,age,date,img,walkingstyle,nickname,phone)values "
			+"(#{id},#{pwd},#{gender},#{age},sysdate(),#{img},#{walkingstyle},#{nickname},#{phone})")
	int createMember(MemberDTO dto);

}
		