package banzzac.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class MemberDTO {

	private int no,gender,age,temperature,cnt,isGrant;
	private String id,pwd,img,nickname,walkingStyleStr;
	private Date date;
	private ArrayList<String> walkingStyle;
	
	
	public void setWalkingStyleStr(String walkingStyleStr) {
		this.walkingStyleStr = walkingStyleStr;
		this.walkingStyle = new ArrayList<>(Arrays.asList(walkingStyleStr.split(",")));
	}
	
	public void setWalkingStyle(ArrayList<String> walkingStyle) {
		this.walkingStyle = walkingStyle;
		this.walkingStyleStr = walkingStyle.toString();
	}
	
	
}
