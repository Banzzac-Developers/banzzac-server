package banzzac.utill;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

@ToString
public class SelectTitle {
	
	@Getter
	private String title;
	
	
	public void selectTitle(String serviceName) {
		String members = "회원 관리 - ";
		String sales = "매출 관리 - ";
		Map<String, String> titles = new LinkedHashMap<>();
		titles.put("board", "대시보드");
		titles.put("newMembers", members+"신규 회원 검수");
		titles.put("chart", sales+"매출");
		
		
		
		
		this.title = titles.get(serviceName);
		
	}
	
	
}
