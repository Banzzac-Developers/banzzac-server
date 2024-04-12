package banzzac.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.PageDTO;
import banzzac.dto.ReportDTO;


@Mapper
public interface AdminMapper {

	@Select("select * from report limit #{serachNo}, #{listCnt}")
	public ArrayList<ReportDTO> getReportMemberList(PageDTO dto);
	
	
	@Select("select count(*) from report")
	public int getTotalReportCount();
	
}
