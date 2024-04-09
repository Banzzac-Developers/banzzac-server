package banzzac.dto;

import java.util.Date;
import org.springframework.stereotype.Component;


import lombok.Data;


@Component
@Data
@Component
public class DogDTO {

	private String id,name,img,gender,neutrification,size,kind,personality,activity;
	private int age,weight;
}
