package banzzac.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.MemberDTO;
import banzzac.dto.PageDTO;
import banzzac.dto.PaymentSuccessDTO;
import banzzac.dto.ReportDTO;


@Mapper
public interface AdminMapper {

	@Select(" <script>"
			+ "SELECT r.*, m1.id AS member_id, m2.id AS reported_id "
			+ "FROM report r "
			+ "JOIN member m1 ON r.member_no = m1.no "
			+ "JOIN member m2 ON r.reported_no = m2.no "
			+ "ORDER BY r.report_status, r.report_time DESC "
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
	
<<<<<<< Updated upstream
	
	
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
	
=======
	@Update("UPDATE member SET isGrant = 2 WHERE id = #{id}")
	public int suspendMember(String id);
	
	@Select("SELECT r.*, m1.id AS member_id, m2.id AS reported_id "
			+ "FROM report r "
			+ "JOIN member m1 ON r.member_no = m1.no "
			+ "JOIN member m2 ON r.reported_no = m2.no "
			+ "where report_no = #{no} ")
	public ReportDTO reportDetail(int no);
	
	@Update("UPDATE report "
			+ "SET report_admin_answer = #{reportAdminAnswer} , report_status = 2 "
			+ "WHERE report_no  = #{reportNo}")
	public int modifyReportDetail(ReportDTO dto);
	
	@Update("UPDATE report "
			+ "SET report_status = 2 "
			+ "WHERE report_no = #{no} ")
	public int modifyReportStatus(int no);
	
	@Select("select * from member where isGrant = 2")
	public ArrayList<MemberDTO> getSuspendMemberList(PageDTO dto);
	
	@Update("UPDATE member "
			+ "SET isGrant = 1 "
			+ "WHERE id = #{id} "
			+ "ORDER BY date")
	public int changeSuspendMember(String id);
	
	@Select("select * from member where isGrant = 0")
	public ArrayList<MemberDTO> getWithdrawalMemberList(PageDTO dto);
	
	
	
	
	


>>>>>>> Stashed changes
}
