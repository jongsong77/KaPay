package com.kakaopay.sc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.entity.QnaRepositoryCustom;
import com.kakaopay.sc.model.Header;
import com.kakaopay.sc.model.Pagination;
import com.kakaopay.sc.model.SearchCondition;
import com.kakaopay.sc.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService 
{
    
    
	private final QnaRepository qnaRepository;
	private final QnaRepositoryCustom qnaRepositoryCustom;
	public List<Qna> getQnaService(String name){
		return qnaRepository.findAll();
	}
	public Header<List<Qna>> getQnaList(Pageable pageable,SearchCondition searchCondition) {
			    
	    Page<Qna> qnaEntities = qnaRepositoryCustom.findAllBySearchCondition(pageable,searchCondition);
	    List<Qna> dtos = new ArrayList<>();
	    for (Qna entity : qnaEntities) {
	        dtos.add(entity);
	    }
	    Pagination pagination = new Pagination(
	            (int) qnaEntities.getTotalElements()
	            , pageable.getPageNumber() + 1
	            , pageable.getPageSize()
	            , 10
	    );
	    return Header.OK(dtos, pagination);
	}
	public Qna getQnaDetail(long idx) {
		return qnaRepository.findFirstByIdx(idx);
	}
	public int updateState(long idx,int state,String agtId,String agtName) {
		return qnaRepository.updateState(idx, state, agtId, agtName);
	}
	public int updateAgtAnswerAndState(long idx,int state,String agtAnswer) {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String answeredAt = now.format(formatter);
		return qnaRepository.updateAgtAnswerAndState(idx,state,agtAnswer,answeredAt);
	}
	
	public Qna createQna(Qna qna) {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formatedNow = now.format(formatter);
		qna.setCreatedAt(formatedNow);
		qnaRepository.save(qna);
		return qna;
	}
	public int updateQna(Qna qna) {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String createdAt = now.format(formatter);
		
		return qnaRepository.updateQna(qna.getIdx(),qna.getInquiry(),createdAt);
	}
}
