package kr.co.farmstory.security;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityUserService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 스프링 시큐리티 인증 동작방식은 아이디/패스워드를 한 번에 조회하는 방식이 아닌 
		// 아이디만 이용해서 사용자 정보를 로딩하고 이후 패스워드를 검증하는 방식이다.

		// security: 사용자 계정 먼저 확인 (username)
		UserEntity user = repo.findById(username).get();
		
		if(user ==  null) {
			throw new UsernameNotFoundException(username);
		}
		
		// MyUserDetails -> @Builder : Builder 방식으로 생성 (build 초기화, build 패턴)
		UserDetails myUser = MyUserDetails.builder()
							.user(user)
							.build();
		
		return myUser;

	}


}
