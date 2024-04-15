package banzzac.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class MemberDTO {


	private int no,gender,age,Temperature,cnt,isGrant, quantity;
	private String id,pwd,img,walkingStyleStr,nickname,phone,statusMessage;

	private Date date;
	private ArrayList<String> walkingStyle;
	
	
	public void setWalkingStyleStr(String walkingStyleStr) {
		this.walkingStyleStr = walkingStyleStr;
		this.walkingStyle = new ArrayList<>(Arrays.asList(walkingStyleStr.split(",")));
	}
	
	public void setWalkingStyle(ArrayList<String> walkingStyle) {
		this.walkingStyle = walkingStyle;
		this.walkingStyleStr = String.join(",", walkingStyle);
	}
	
	public String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(date);
	}
	
	
}
