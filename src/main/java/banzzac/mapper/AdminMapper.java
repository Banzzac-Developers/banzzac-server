package banzzac.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import banzzac.dto.PageDTO;
import banzzac.dto.PaymentSuccessDTO;
import banzzac.dto.ReportDTO;


@Mapper
public interface AdminMapper {

	@Select(" <script>"
			+ "select * from report "
			+ " order by report_time desc"
			+ " <if test='searchNo != null' >"
				+ " limit #{searchNo}, #{listCnt} "
			+ " </if>"
			+ " <if test='searchNo == null' >"
				+ " limit 0,#{listCnt}"
			+ " </if>"
			+ " </script>")
	public ArrayList<ReportDTO> getReportMemberList(PageDTO dto);
	
	
	@Select("select count(*) from report")
	public int getTotalReportCount();
	
	
	
	/***************** 어매성 ************************/
	@Select("select "
			+ "DATE(approved_at), count(*),"
			+ "sum(quantity),sum(total_amount) "
			+ "from paymentsuccess p "
			+ "group by  DATE(approved_at)")
	public ArrayList<PaymentSuccessDTO> dailySales(); 
	
	/** 월별 결제 건수 */
	@Select("SELECT m.month_number, "
			+ "       COALESCE(COUNT(p.month_number), 0) AS montlySaleCnt "
			+ "FROM ("
			+ "    SELECT 1 AS month_number UNION ALL"
			+ "    SELECT 2 UNION ALL"
			+ "    SELECT 3 UNION ALL"
			+ "    SELECT 4 UNION ALL"
			+ "    SELECT 5 UNION ALL"
			+ "    SELECT 6 UNION ALL"
			+ "    SELECT 7 UNION ALL"
			+ "    SELECT 8 UNION ALL"
			+ "    SELECT 9 UNION ALL"
			+ "    SELECT 10 UNION ALL"
			+ "    SELECT 11 UNION ALL"
			+ "    SELECT 12"
			+ ") AS m "
			+ "LEFT JOIN ("
			+ "    SELECT MONTH(approved_at) AS month_number"
			+ "    FROM paymentsuccess"
			+ ") AS p ON m.month_number = p.month_number "
			+ "GROUP BY m.month_number;")
	public int montlySalesCount(); 
	
}
