package kr.co.farmstory.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kr.co.farmstory.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//userDts는 아이디와 패스워드만 포함 -> 이름, 번호 등 다른 페이지에 출력하기 위해서는 커스텀 필요 -> MyUserDetails
// 로그인하면 MyUserDetails 생성
@Setter
@Getter
@Builder
public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserEntity user;
	
	
	// 사용자 권한 목록 - 로그인한 계정이 갖는 권한 목록 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getGrade()));
		return authorities;
	}

	// 계정이 갖는 비밀번호
	@Override
	public String getPassword() {
		return user.getPass();
	}

	// 계정이 갖는 아이디
	@Override
	public String getUsername() {
		return user.getUid();
	}

	// 계정 만료 여부 
	// true - 만료 안됨, false - 만료(로그인 불가)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠김 여부 
	// true - 잠김 안됨, false - 잠김(로그인 불가)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정 비밀번호 만료 여부
	// true - 만료 안됨, false - 만료(로그인 불가)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화 여부
	// true - 활성화, false - 비활성화(로그인 불가)
	@Override
	public boolean isEnabled() {
		return true;
	}

}
