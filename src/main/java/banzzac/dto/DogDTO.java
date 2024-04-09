package banzzac.dto;

import lombok.Data;

@Data
@Component
public class DogDTO {

	private String id,name,img,gender,neutrification,size,kind,personality,activity;
	private int age,weight;
}
