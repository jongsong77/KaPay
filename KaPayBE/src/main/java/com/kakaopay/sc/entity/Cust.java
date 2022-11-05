package com.kakaopay.sc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cust {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	private String userId;
}
