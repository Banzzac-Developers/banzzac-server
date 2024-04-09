package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.DogDTO;
import banzzac.dto.MemberDTO;

@Mapper
public interface ProfileMapper {
	
	/** 내 목록 불러오기 */
	//@Select("select * from member where id = 세션아이디")
	List<MemberDTO> memberList();
	
	
	int modify();
	
	
	

}
