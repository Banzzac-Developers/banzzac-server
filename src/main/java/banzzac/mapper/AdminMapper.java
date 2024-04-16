package banzzac.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.DashBoardDTO;
import banzzac.dto.MemberDTO;
import banzzac.dto.PageDTO;
import banzzac.dto.PaymentSuccessDTO;
import banzzac.dto.ReportDTO;
import banzzac.dto.SalesManagementDTO;


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

	/***************** 어매성 ************************/
	/** 환불 테이블도 같이 읽어오기 */
	/** 7일 전까지 결제 내역 */
	@Select("select "
			+ "COUNT(partner_order_id) as order_cnt,"
			+ "SUM(quantity) as quantity,"
			+ "SUM(total_amount) as total_amount "
			+ "from paymentsuccess "
			+ "where datediff(curdate(),approved_at) <=7 "
			+ "GROUP BY approved_at "
			+ "order by approved_at desc;")
	public ArrayList<SalesManagementDTO> dailySales(); 
	
	/** 주간 결제 내역 */
	
	/** 월별 결제 내역 */
	@Select("SELECT  "
			+ "    p.year_num AS year, "
			+ "    m.month_number AS month, "
			+ "    COALESCE(p.order_num, 0) AS order_cnt, "
			+ "    COALESCE(SUM(p.quantity), 0) AS quantity, "
			+ "    COALESCE(SUM(p.total_amount), 0) AS total_amount "
			+ "FROM ( "
			+ "    SELECT DISTINCT YEAR(approved_at) AS year_num  "
			+ "    FROM paymentSuccess "
			+ "    WHERE YEAR(approved_at) = #{year} "
			+ ") AS years "
			+ "CROSS JOIN ( "
			+ "    SELECT 1 AS month_number UNION ALL "
			+ "    SELECT 2 UNION ALL "
			+ "    SELECT 3 UNION ALL "
			+ "    SELECT 4 UNION ALL "
			+ "    SELECT 5 UNION ALL "
			+ "    SELECT 6 UNION ALL "
			+ "    SELECT 7 UNION ALL "
			+ "    SELECT 8 UNION ALL "
			+ "    SELECT 9 UNION ALL "
			+ "    SELECT 10 UNION ALL "
			+ "    SELECT 11 UNION ALL "
			+ "    SELECT 12 "
			+ ") AS m "
			+ "LEFT JOIN ( "
			+ "    SELECT  "
			+ "        MONTH(approved_at) AS month_number, "
			+ "        YEAR(approved_at) AS year_num, "
			+ "        COUNT(partner_order_id) AS order_num, "
			+ "        SUM(quantity) AS quantity, "
			+ "        SUM(total_amount) AS total_amount "
			+ "    FROM paymentSuccess   "
			+ "    WHERE YEAR(approved_at) = #{year} "
			+ "    GROUP BY month_number "
			+ ") AS p ON years.year_num = p.year_num AND m.month_number = p.month_number "
			+ "WHERE (years.year_num = YEAR(CURDATE()) AND m.month_number <= MONTH(CURDATE())) "
			+ "   OR (years.year_num < YEAR(CURDATE())) "
			+ "GROUP BY p.year_num, m.month_number "
			+ "ORDER BY p.year_num DESC, m.month_number DESC;")
	public ArrayList<SalesManagementDTO> montlySales(int year);
	
	@Select("select year(p.approved_at) as year "
			+ "from paymentsuccess p "
			+ "group by year "
			+ "order by p.approved_at desc	")
	public ArrayList<SalesManagementDTO> selectYear();
	
	@Select("SELECT YEAR(approved_at) AS year,"
			+ "       SUM(quantity) AS total_quantity,"
			+ "       SUM(total_amount) AS total_amount,"
			+ "       COUNT(partner_order_id) AS order_count "
			+ "FROM paymentSuccess "
			+ "GROUP BY YEAR(approved_at); ")
	public ArrayList<SalesManagementDTO> yearSales();
	
	
	@Select("SELECT  "
			+ "  partner_user_id as user_id, "
			+ "  ranking as ranking, "
			+ "  month_num as month, "
			+ "  year_num as year"
			+ "FROM ( "
			+ "    SELECT  "
			+ "        p.partner_user_id, "
			+ "        MONTH(approved_at) as month_num, "
			+ "        year(approved_at) as year_num, "
			+ "        RANK() OVER (PARTITION by month(p.approved_at) ORDER BY SUM(p.total_amount) DESC) AS ranking "
			+ "    FROM  "
			+ "        paymentsuccess p "
			+ "	   where not exists ( "
			+ "			select partner_order_id  "
			+ "			from refund r "
			+ "			where r.partner_order_id = p.partner_order_id  "
			+ "			)"
			+ "    GROUP BY  "
			+ "        p.partner_user_id,month_num "
			+ "     having year_num = #{year} and month_num=#{month} "
			+ "    order by month_num,year_num desc,ranking asc "
			+ ") AS ranked_table "
			+ "where ranking<=3")
	public ArrayList<SalesManagementDTO> ranking(int year, int month);
	
	@Select("select "
			+ "r.partner_order_id as order_id,"
			+ "r.reason as reason,"
			+ "r.approve as refund_status,"
			+ "r.refund_request_date as refund_request,"
			+ "r.approve_time as refund_approve,"
			+ "p.quantity as quantity,"
			+ "p.total_amount as total_amount,"
			+ "p.tid as tid,"
			+ "p.partner_user_id as user_id,"
			+ "p.approved_at as pay_date "
			+ "from refund r "
			+ "join paymentsuccess p  "
			+ "on r.partner_order_id = p.partner_order_id  "
			+ "where r.approve = #{refundStatus} "
			+ "order by r.refund_request_date desc")
	public ArrayList<SalesManagementDTO> refund(int refundStatus);
	
	
	/************************ 어매성 ************************/
  
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
	
	// 정운만 시작#############################################
	
	@Select("SELECT date_range.date AS daily_range, "
			+ "COALESCE(SUM(ps.total_amount), 0) AS total_amount "
			+ "FROM ( "
			+ "SELECT DATE_SUB(CURRENT_DATE(), INTERVAL (t*10 + u) DAY) AS date "
			+ "FROM "
				+ "(SELECT 0 AS t "
				+ "UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL "
				+ "SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9)"
			+ " AS tens, "
				+ "(SELECT 0 AS u UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL "
				+ "SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS units "
				+ "WHERE DATE_SUB(CURRENT_DATE(), INTERVAL (t*10 + u) DAY) BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY) AND DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY) "
				+ ")"
			+ " AS date_range "
			+ "LEFT JOIN paymentSuccess ps"
			+ " ON DATE(ps.approved_at) = date_range.date "
			+ "GROUP BY date_range.date"
			+ " ORDER BY date_range.date DESC")
	public ArrayList<SalesManagementDTO> calculateDailyPay();
	
	@Select("select (select count(*) from member where Date(date) = curdate() and isGrant = 2) as today_register, "
				+ "	(select count(*) from report where report_status != 2 ) as report_count, "
				+ "	(select count(*) from member where Date(date) = curdate() and isGrant = 0) as today_withdrawn_member, "
				+ "	(select count(*) from refund where approve = 2) as refund_count")
	public DashBoardDTO getTodayEvent();
	// 정운만 끝 ##############################################
}
