package banzzac.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.MatchingDTO;

@Mapper
public interface MatchingMapper {

	/** 회원가입 시 insertMachingCondition이 기본 값으로 설정 될 수 있도록 만들어야 합니다.*/
	@Insert("insert into matching_conditions (no) values (#{no})")
	public int insertMatchingCondition(MatchingDTO dto);
	
	
	@Select("select * from matching_conditions where no = #{no}")
	public MatchingDTO showMatchingCondition(MatchingDTO dto);
	
	
	/***/
	@Update("Update matching_conditions set"
			+ "stlye = #{style},"
			+ " age_range_start = #{ageRangeStart},"
			+ " age_range_end=#{ageRangeEnd},"
			+ " gender=#{gender},"
			+ " gender=#{size},"
			+ " dog_nature=#{dogNatureStr},"
			+ " amount_of_activity=#{amountOfActivity},"
			+ " want_matching=#{wantMatching} where no=#{no}")
	public int updateMatchingCondition(MatchingDTO dto);
	
	
	/** 
	 * 경우에 따라 분기를 나눠 주셔야 합니다.
	 * 유저가 계정을 삭제할 때, 정지 당할 때, 매칭을 원하지 않을 때로 나뉩니다.
	 * 유저가 계정을 삭제할 때, 정지 당할 때는 wantMatching에 0을 넣어주세요.
	 * 유저가 매칭을 원할 때, 원하지 않을 때는 Front에서 받아온 값으로 설정합니다.
	 * */
	@Update("Update matching_conditions set"
			+ " want_matching = #{wantMatching} where no=#{no}")
	public int updateWantMatching(MatchingDTO dto);
	
	
	
	
}

