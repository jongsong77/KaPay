package com.kakaopay.sc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.kakaopay.sc.entity.Cust;
import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.model.Header;
import com.kakaopay.sc.repository.CustRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustService {
	
	private final CustRepository custRepository;
	private final QnaService qnaService;

}
