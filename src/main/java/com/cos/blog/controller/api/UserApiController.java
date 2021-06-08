package com.cos.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDto<String, Integer> save(@RequestBody User user) {
		userService.회원가입(user);
		return new ResponseDto<String, Integer>(null, HttpStatus.OK, HttpStatus.OK.value());
	}

	// username 중복 체크
	@GetMapping("/auth/user/chk/{username}")
	public ResponseEntity<Boolean> chkUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.checkUsername(username));
	}

	/*
	 * 전통적인 로그인 방법
	 * 
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session) { User principal =
	 * userService.로그인(user);
	 * 
	 * if(principal != null) { session.setAttribute("principal",
	 * principal.getUsername()); } return new
	 * ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
	 */

	@PutMapping("/api/user/{id}")
	public ResponseDto<String, Integer> updateUser(@PathVariable int id, @RequestBody User user, Exception e) throws Exception {
		
		userService.회원수정(id, user);
		
		// 강제로 로그인을 시켜서 새로 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<String, Integer>(null, HttpStatus.OK, HttpStatus.OK.value());
	}
}
