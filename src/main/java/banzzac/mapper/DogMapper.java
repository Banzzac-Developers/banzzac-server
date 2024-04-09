package banzzac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import banzzac.dto.DogDTO;

@Mapper
public interface DogMapper {

	@Insert("insert into dog"
			+"(id,name,age,img,gender,weight,neutrification,size,kind,personality,activity)values"
			+"(#{id},#{name},#{age},#{img},#{gender},#{weight},#{neutrification},#{size},#{kind},#{personality},#{activity})"
			)
	int createDog(DogDTO dto);
}
		