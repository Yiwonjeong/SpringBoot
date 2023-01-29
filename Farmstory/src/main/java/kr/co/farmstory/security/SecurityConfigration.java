package kr.co.farmstory.security;

import kr.co.farmstory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigration {
	
	@Autowired
	private SecurityUserService service;

	@Autowired
	private DataSource dataSource;

	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll();
		
		// 사이트 위변조 요청 방지
		http.csrf().disable();


		// 로그인 설정
		http.formLogin()
		.loginPage("/user/login")
		.defaultSuccessUrl("/index")
		.failureUrl("/user/login?success=100")
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/user/login?success=200");
		
		// 사용자 인증 처리 컴포넌트 서비스 등록
		http.userDetailsService(service);

		// 자동 로그인 설정
		http.rememberMe()
		.rememberMeParameter("rememberMe")		// html checkbox name에 해당하는 값 (default: remember-me)
		.tokenValiditySeconds(86400*30)			// 한 달 (default: 14일)
		.alwaysRemember(false)					// 체크 박스 항상 실행 -> (default: false)
		.userDetailsService(service)			// 사용자 계정 조회 처리 설정 api
		.tokenRepository(tokenRepository());

		return http.build();
	}

	// 로그인 유지 - 토큰 저장소
	@Bean
	public PersistentTokenRepository tokenRepository(){
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();	// DB persistent_logins sql 구현체
		jdbcTokenRepository.setDataSource(dataSource);

		return jdbcTokenRepository;
	}


	// 비밀번호 암호화 설정
	@Bean
    public PasswordEncoder PasswordEncoder () {
		return new BCryptPasswordEncoder();
    }



}
