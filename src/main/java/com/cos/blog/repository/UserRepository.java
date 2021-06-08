package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// 스프링 bean 자동 등록 되어서 @Repository 생략 가능 DI 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	// JPA Naming 쿼리
	boolean existsByUsername(String username);
	//findBy = SELECT * FROM user WHERE username = ? AND password =?  
	//User findByUsernameAndPassword(String username, String password);
	/*
	 * nativeQuery
	 * 
	 * @Query(value = "SELECT * FROM user WHERE username = ? AND password =?",
	 * nativeQuery = true) User login(String username, String password);
	 */
	
	Optional<User> findByUsername(String username);
}
