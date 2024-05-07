package banzzac.jwt;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import banzzac.dto.MemberDTO;
import banzzac.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {
	
	
	private final LoginMapper loginMapper;
	
	/** 해당 유저가 있다면 그 유저에 대한 정보를 반환합니다.
	 * Security 에서 제공하는 UserDetails 인터페이스를 구현한 MemberDetail을 반환하게 만들었습니다.
	 * 해당 권한은 숫자로 제공되나 String으로 변환하여 List에 추가하였습니다.
	 * 이 값은 Security Config에서 권한 확인 용도로 사용됩니다.*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDTO member = loginMapper.loginId(username);
		 if (member == null) {
	            // user가 null인 경우 예외 발생
	            throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
	     }
		
	
		List<GrantedAuthority> authorities = new ArrayList<>();
		 
		authorities.add(new SimpleGrantedAuthority(member.getIsGrant()+""));
		
		MemberDetail user = new MemberDetail(member.getId(),member.getPwd(),authorities);
		
		user.setImg(member.getImg());
		user.setIsGrant(member.getIsGrant());
		user.setNickname(member.getNickname());
		user.setNo(member.getNo());
		
		return user;
	}

	public MemberDTO checkLoin(String username, String password) throws LoginException {
        MemberDTO user = loginMapper.loginId(username);

        if (user == null) {
            // user가 null인 경우 예외 발생
            throw new LoginException("유저를 찾을 수 없습니다.");
        }
        
        // password check
        if(!password.equals(user.getPwd()))
            throw new LoginException("password error");

        return user;
    }

	
	
	
}
