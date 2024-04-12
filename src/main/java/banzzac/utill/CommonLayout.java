package banzzac.utill;

import jakarta.annotation.Resource;
import lombok.Data;

@Data
public class CommonLayout {
	private String folder;
	private String service;
	@Resource
	private Paging page; 
	
}
