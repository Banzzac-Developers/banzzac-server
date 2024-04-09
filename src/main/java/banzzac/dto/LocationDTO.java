package banzzac.dto;

import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import lombok.Data;

@Data
@Component
public class LocationDTO {
	@Resource
	private DogDTO dogDTO;
	//현재 내 위치를 가져옵니다.
	private Double longitude, latitude;
	//북동쪽 범위
	private Double [] rangeNorthEast;
	//남서쪽 범위
	private Double [] rangeSouthWest;
	
}
