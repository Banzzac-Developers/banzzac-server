package banzzac.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MemberDTO {

	private int no,gender,age,temperature,cnt,isGrant;
	private String id,pwd,img,walkingStyle,nickname;
	private Date date;
}
