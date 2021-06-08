package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링 Ioc 메모리에 띄워줌
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	public boolean checkUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encode.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void 회원수정(int id, User requestUser) throws Exception {
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		if(user.getOauth() == null || user.getOauth().equals("")) {
			user.setPassword(encode.encode(requestUser.getPassword()));
			user.setEmail(requestUser.getEmail());			
		}
		
	}
	
	@Transactional(readOnly=true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	
	
	/*
	 * @Transactional(readOnly = true) // SELECT할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
	 * 유지) public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
}
