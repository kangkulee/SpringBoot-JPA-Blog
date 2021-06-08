package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity
// @DynamicInsert // Insert 시에 null 인 필드를 제거해줌
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //DB에 설정된 넘버링 전략을 따라감
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// DB는 RoleType이라는게 없다. 때문에 아래 어노테이션으로 String 명시
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. // ADMIN, USER
	
	private String oauth;
	
	@CreationTimestamp // 시간이 자동 입력 됨.
	private Timestamp createDate;
	
}
