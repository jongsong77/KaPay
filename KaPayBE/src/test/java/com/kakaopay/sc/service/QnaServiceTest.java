package com.kakaopay.sc.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.repository.QnaRepository;

@SpringBootTest
class QnaServiceTest {
	@Autowired
	QnaRepository qnaRepository;
	@DisplayName("1. 게시글 생성하기")
	@Test
	void insert() {
		Qna qna = new Qna();
		qna.setTitle("test_title");
		qna.setState(0);
		qna.setInquiry("test_inquiry_content");
		qna.setCustId("jschoi");
		Qna saved = qnaRepository.save(qna);
		assertThat(qna.getTitle()).isEqualTo(saved.getTitle());
	}
	@DisplayName("2. 게시글 리스트 조회")
	@Test
	void getList() {
		Pageable pageable = PageRequest.of(0, 5);
		Page<Qna> q = qnaRepository.findAllByOrderByIdxDesc(pageable);
        System.out.println("PAGE SIZE: " + q.getSize());
        System.out.println("TOTAL PAGE: " + q.getTotalPages());
        System.out.println("TOTAL COUNT: " + q.getTotalElements());
        System.out.println("NEXT: " + q.nextPageable());
	}
	@DisplayName("3. 게시글 내용수정")
	@Test
	void update() {
		long idx =1;
		String inquiry = "test_update";
		String createdAt = "2022-11-06 01:01:01";
		int res = qnaRepository.updateQna(idx,inquiry,createdAt);
        System.out.println("update_res:" + res);
	}
	@DisplayName("4. 게시글 담당지정")
	@Test
	void assign() {
		long idx =1;
		int state = 1;
		String agtId ="test_agt";
		String agtName ="test_name";
		int res = qnaRepository.updateState(idx,state,agtId,agtName);
        System.out.println("assign_res:" + res);
	}		
	@DisplayName("5. 게시글 답변")
	@Test
	void reply() {
		long idx =1;
		int state = 2;
		String agtAnswer ="test_answer";
		String answeredAt = "2022-11-06 01:01:01";
		int res = qnaRepository.updateState(idx,state,agtAnswer,answeredAt);
        System.out.println("reply_res:" + res);
	}
}
