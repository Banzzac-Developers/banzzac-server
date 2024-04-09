package banzzac.dto;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MatchingDTO {
	
	private Integer no;
	private String stlye;
	private Integer ageRangeStart;
	private Integer ageRangeEnd;
	private String gender;
	private String size;
	private String dogNatureStr;
	private ArrayList<String> dogNature;
	private String amountOfActivity;
	private boolean wantMatching;
	
	public void setDogNatureStr(String dogNatureStr) {
		this.dogNatureStr = dogNatureStr;
		this.dogNature = new ArrayList<>();
		for (String nature : dogNatureStr.split(",")) {
			dogNature.add(nature);
		}
		System.out.println("DogNature ===> "+ dogNatureStr);
	}
	public void setDogNature(ArrayList<String> dogNature) {
		this.dogNature = dogNature;
		this.dogNatureStr = dogNature.toString();
		System.out.println("DogNatureStr ===> "+ dogNatureStr);
	}
	
	
	
	
}
