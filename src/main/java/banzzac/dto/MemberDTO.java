package banzzac.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDTO {

	private int no,gender,age,Temperrature,cnt,isGrant;
	private String id,pwd,img,walkingStyle,nickName;
	private Date date;
}
