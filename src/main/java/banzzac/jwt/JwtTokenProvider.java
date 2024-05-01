package banzzac.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import banzzac.mapper.MemberMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtTokenProvider {
	
	
	@Resource
	private final MemberMapper memberMapper;
	
	@Value("${spring.jwt.secret}")
	private String secretKey;
	private final long tokenValidMillSecond = 1000L * 60 * 60 * 6;
	private final long tokenValidRefresh = 1000L * 60 * 60 * 24 * 3;
	
	
	
}
