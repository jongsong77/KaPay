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
public class Qna {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;
	private String custId;           //고객 ID
	private String agtId;            //상담원 ID
	private String agtName;          //상담원이름
	private String title;			 //제목
	private String inquiry; 		 //문의내용
	private String agtAnswer;   	 //상담사답변
	private int state;          	 //상담상태 0:상담원 미배정 1:상담배정 2:상담완료
	private String createdAt; //생성시간
	private String answeredAt;       //답변시간
	
	
}
