package kr.co.farmstory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigration{
	
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
		.loginPage("/user/login")										// 인증이 필요한 URL에 접근하면 /user/login으로 이동
		.defaultSuccessUrl("/index")									// 로그인 성공하면 "/index" 로 이동
		.failureUrl("/user/login?success=100")	//로그인 실패 시 /user/login?success=100으로 이동
		.usernameParameter("uid")										// 로그인 시 form에서 가져올 값
		.passwordParameter("pass");										// 로그인 시 form에서 가져올 값

		// 구글 로그인 설정
		http.oauth2Login()
		.loginPage("/user/login");

		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/user/login?success=200");					// 로그아웃 성공 시 "/user/login?success=200"으로 이동
		
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

	// 등록된 AuthenticationManager을 불러오기 위한 Bean
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}



}
