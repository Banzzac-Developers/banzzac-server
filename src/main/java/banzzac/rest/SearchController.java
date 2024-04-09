package banzzac.rest;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banzzac.dto.LocationDTO;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	
	
	@PostMapping
	public ArrayList<LocationDTO> getLocations(@RequestBody LocationDTO dto){
		System.out.println(dto);
		
		return null;
	}
	
}
