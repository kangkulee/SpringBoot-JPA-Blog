package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {
	
	@GetMapping("/http/lombok")
	public String httpTest() {
		Member m = new Member(4, "이강구", "449595", "dkwkdehf@naver.com");
		Member m1 = Member.builder().username("되나요?").email("haha@naver.com").build();
		System.out.println(m1.getId());
		System.out.println(m.getUsername());
		m.setUsername("나야나");
		System.out.println(m.getUsername());
		System.out.println(m1.getUsername());
		System.out.println(m1.getEmail());
		return "test 완료";
	}
	
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청 : " + m.getId() + " " + m.getUsername() + m.getPassword() + m.getEmail();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청 : " + m.getId() + " " + m.getUsername() + m.getPassword() + m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
