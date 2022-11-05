package com.kakaopay.sc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Agent {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	private String agtname;
	@Column(name="name", unique=true)
	private String agtId;
	private String agtPw;
	
}
