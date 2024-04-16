package banzzac.dto;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class SalesManagementDTO {
	private int orderCnt,totalAmount, quantity;
	private int refundQuantity,refundCnt,refundAmount;
	private Date dailyRange;
	private ArrayList<Integer> month;
	
	
	/*
	 * public void daily(){ LocalDate today = LocalDate.now(); ArrayList<LocalDate>
	 * sevenDays = new ArrayList<>(); for (int i = 0; i < 7; i++) { LocalDate
	 * dateBefore = today.minusDays(i); sevenDays.add(dateBefore); } this.dailyRange
	 * = sevenDays; }
	 */
}
