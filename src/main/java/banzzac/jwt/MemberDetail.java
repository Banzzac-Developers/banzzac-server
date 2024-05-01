package banzzac.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import banzzac.dto.MemberDTO;
import jakarta.annotation.Resource;

/** Spring Security 에서 제공하는 Interface를 구현하여 인증에 관련된 메소드들을 구현합니다.*/
public class MemberDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private MemberDTO memberDTO;
	/** 계정의 권한 목록들 가져오는 메소드*/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.getAuthorities();
	}
	
	
	@Override
	public String getPassword() {
		return null;
	}
	
	
	/** JsonProperty read_write 권한,
	 * 아이디를 반환.*/
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public String getUsername() {
		return memberDTO.getId();
	}

	
	/** 계정이 만료되었는 지 가져오는 메소드 true 시 계정 만료X , False에 만료 O*/
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	
	
	/** 계정이 잠겼는지 확인하는 메소드 true 시 계정 만료 X, 위와 같음*/
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	/** 계정이 비밀번호가 만료 되었는지 확인하는 메소드, 위와 같음.*/
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
		// TODO Auto-generated method stub
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	
	/** 계정이 활성화 되었는지 확인하는 메소드, true가 활성화 상태.*/
	@JsonProperty(access = Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
