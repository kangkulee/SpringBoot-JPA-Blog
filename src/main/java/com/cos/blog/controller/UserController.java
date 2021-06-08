package com.cos.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.User;
import com.cos.blog.service.KakaoService;
import com.cos.blog.service.UserService;

@Controller
public class UserController {

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private UserService userService;

	@Value("${blog.blogKey}")
	private String key;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {
		String kakaoAccessToken = kakaoService.카카오토큰받기(code);
		KakaoProfile kakaoProfile = kakaoService.사용자정보가져오기(kakaoAccessToken);

		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(key)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();

		User originUser = userService.회원찾기(kakaoUser.getUsername());

		if (originUser.getUsername() == null) {
			userService.회원가입(kakaoUser);
		}

		// 로그인 처리
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), key));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";
	}

	@GetMapping("/user/userDetailForm")
	public String userDetailForm(@AuthenticationPrincipal PrincipalDetail principal, Model model) {
		model.addAttribute("user", principal.getUser());
		return "userDetailForm";
	}
}
