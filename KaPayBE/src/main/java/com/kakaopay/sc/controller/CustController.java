package com.kakaopay.sc.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.sc.entity.Cust;
import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.model.Header;
import com.kakaopay.sc.model.SearchCondition;
import com.kakaopay.sc.service.CustService;
import com.kakaopay.sc.service.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class CustController {
	
	private final CustService custService;
	private final QnaService qnaService;
    @GetMapping("/myqnalist") //작성글 리스트 조회
    public Header<List<Qna>> qnaList(@PageableDefault(sort = {"idx"}) Pageable pageable,
    		SearchCondition searchCondition ,String custId) {
    	return qnaService.getQnaList(pageable,searchCondition);
    }
    @GetMapping("/qnaDetail") //작성글 조회
    public Qna getMyQna(@RequestParam(required = true) long idx) { 
    	return qnaService.getQnaDetail(idx);
    }
    @PostMapping(value="/sendqna") //글작성
    public Qna sendQna(@RequestBody Qna qna) {
    	return qnaService.createQna(qna);
    }
    @PostMapping(value="/updateqna") //글수정
    public String updateQna(@RequestBody Qna qna) {
    	int result = qnaService.updateQna(qna);
    	if(result > 0) return "SUCCESS";
    	else return "FAIL";
    }
    
}
