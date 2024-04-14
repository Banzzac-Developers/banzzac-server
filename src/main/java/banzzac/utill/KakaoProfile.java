package banzzac.utill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Data;

@Data
public class KakaoProfile {
	
	private Integer id;
    private LocalDateTime connectedAt;
    private String email;
    private String nickname;
    private String gender;
    private String phoneNumber;
    private String ageRange;

    public KakaoProfile(String jsonResponseBody){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonResponseBody);

        this.id = element.getAsJsonObject().get("id").getAsInt();

        String connected_at = element.getAsJsonObject().get("connected_at").getAsString();
        connected_at = connected_at.substring(0, connected_at.length() - 1);
        LocalDateTime connectDateTime = LocalDateTime.parse(connected_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.connectedAt = connectDateTime;

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        this.nickname = properties.getAsJsonObject().get("nickname").getAsString();
        System.out.println("properties : "+ properties);
        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
        System.out.println("kakaoAccount : "+ kakaoAccount);
        this.email = kakaoAccount.getAsJsonObject().get("email").getAsString();
        this.phoneNumber = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();
        this.ageRange = kakaoAccount.getAsJsonObject().get("age_range").getAsString();
        this.gender = kakaoAccount.getAsJsonObject().get("gender").getAsString();
    }
}
