package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.ch08.repository.User2Repo;
import kr.co.ch08.vo.User2VO;

@Service
public class SecurityUserService implements UserDetailsService{

	@Autowired
	private User2Repo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 스프링 시큐리티 인증 동작방식은 아이디/패스워드를 한 번에 조회하는 방식이 아닌 
		// 아이디만 이용해서 사용자 정보를 로딩하고 이후 패스워드를 검증하는 방식이다.
		
		// security: 사용자 계정 먼저 확인 (username)
		User2VO user = repo.findById(username).get();
		
		if(user ==  null) {
			throw new UsernameNotFoundException(username);
		}
		
		/*
		// Security 기본 사용자 객체 생성 : 인증 거치는 과정 (사용자가 폼에 입력한 정보가 맞는지 확인) -> userDts를 세션에 등록
		UserDetails userDts = User.builder()
								  .username(user.getUid())
								  .password(user.getPass())
								  .roles("MEMBER")	//마지막에 권한 주기
								  .build();
		return userDts;
		*/
		
		/* (1) setter로 생성 : userDts는 아이디와 패스워드만 포함 -> 이름, 번호 등 다른 페이지에 출력하기 위해서는 커스텀 필요 -> MyUserDetails
		MyUserDetails myUser = new MyUserDetails();
		myUser.setUid(user.getUid());
		myUser.setPass(user.getPass());
		myUser.setName(user.getName());
		myUser.setHp(user.getHp());
		myUser.setAge(user.getAge());
		myUser.setRdate(user.getRdate());
		
		return myUser;
		*/
		
		
		// (2) MyUserDetails -> @Builder : Builder 방식으로 생성 (build 초기화, build 패턴)
		UserDetails myUser = MyUserDetails.builder()
							.uid(user.getUid())
							.pass(user.getPass())
							.name(user.getName())
							.grade(user.getGrade())
							.hp(user.getHp())
							.age(user.getAge())
							.rdate(user.getRdate())
							.build();
		
		return myUser;
	}
	
}
