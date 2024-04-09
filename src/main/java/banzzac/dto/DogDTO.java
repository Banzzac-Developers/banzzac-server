package banzzac.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DogDTO {

	private String id,name,img,gender,neutrification,size,kind,personality,activity;
	private int age,weight;
	private Date date;
}
