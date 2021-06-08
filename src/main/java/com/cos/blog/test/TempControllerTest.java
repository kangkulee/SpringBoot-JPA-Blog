package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	@GetMapping("/home/html")
	public String tempHtml(Model model) {
		Member m = Member.builder().id(1).username("이강구").password("232323").email("hosol@naver.com").build();
		model.addAttribute("member", m);
		return "home";
	}
}
