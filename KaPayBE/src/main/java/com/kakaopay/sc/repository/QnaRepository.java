package com.kakaopay.sc.repository;

import javax.transaction.Transactional;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kakaopay.sc.entity.Qna;



@Repository
public interface QnaRepository extends JpaRepository<Qna,Long>{
	
	
	Page<Qna> findAllByOrderByIdxDesc(Pageable pageable);
	
	Qna findFirstByIdx(long idx);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Qna SET state = :state,agtId= :agtId,agtName= :agtName where idx =:idx AND state NOT IN(1,2)")
	int updateState(@Param(value="idx") long idx,@Param(value="state") int state,@Param(value="agtId")String agtId,@Param(value="agtName")String agtName);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Qna SET state = :state,agtAnswer= :agtAnswer,answeredAt= :answeredAt where idx =:idx AND state=1")
	int updateAgtAnswerAndState(@Param(value="idx") long idx,@Param(value="state") int state,@Param(value="agtAnswer")String agtAnswer,@Param(value="answeredAt")String answeredAt);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Qna SET inquiry= :inquiry,createdAt= :createdAt where idx =:idx AND state=0")
	int updateQna(@Param(value="idx") long idx,@Param(value="inquiry") String inquriy,@Param(value="createdAt")String createdAt);
}
