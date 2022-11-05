package com.kakaopay.sc.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.kakaopay.sc.model.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import static com.kakaopay.sc.entity.QQna.qna;

@RequiredArgsConstructor
@Repository
public class QnaRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<Qna> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition) {
        JPAQuery<Qna> query = queryFactory.selectFrom(qna)
              .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()),
        		searchKeywords(searchCondition.getSk2(), searchCondition.getSv2()));      		
//                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()),eqAgtId(agtId));
        
//        .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()),
//        		searchKeywords(searchCondition.getSk2(), searchCondition.getSv2()));
        
//        JPAQuery<Qna> query = queryFactory.selectFrom(qna).where(qna.agtId.eq("jongsong77")).where();

        long total = query.stream().count();   //여기서 전체 카운트 후 아래에서 조건작업

        List<Qna> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qna.idx.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression searchKeywords(String sk, String sv) {
        if("custId".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return qna.custId.eq(sv);
            }
        } else if ("title".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return qna.title.eq(sv);
            }
        } else if ("inquiry".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return qna.inquiry.eq(sv);
            }
        } else if("state".equals(sk)) {
        	if(StringUtils.hasLength(sv)) {
                return qna.state.eq(Integer.parseInt(sv));
            }
        } else if("agtId".equals(sk)) {
        	if(StringUtils.hasLength(sv)) {
                return qna.agtId.eq(sv);
            }
        }

        return null;
    }
    private BooleanExpression eqAgtId(String agtId){
        if(!StringUtils.hasText(agtId))  return null;
        return qna.agtId.eq(agtId);
}
}