package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 -> 스프링 컨테이너에서 객체를 관리 할 수 있게 하는 것

@Configuration // 빈등록(Ioc 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. 필터설정을 위함
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크 하겠다는 뜻. 요3개의 어노테이션은 세트임.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC가 되어서 리턴값을 스프링에서 관리함
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}



	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests() // -> 요청이 오면
				.antMatchers("/","/auth/**", "/js/**", "/css/**", "/images/**") // -> ()안과 매치되면
				.permitAll() // -> 모든접근가능
				.anyRequest() // -> 매치되지않는 그외 요청
				.authenticated() // -> 인증(로그인)이 되어야함
			.and() // -> 그리고
				.formLogin() // -> 로그인페이지로 보내는데
				.loginPage("/auth/loginForm") // -> 로그인 페이지 주소를 써준다.
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 진행
				.defaultSuccessUrl("/"); // 로그인 성공시 보내는 url 주소
	}
}
