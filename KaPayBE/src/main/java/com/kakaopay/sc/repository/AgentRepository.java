package com.kakaopay.sc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.sc.entity.Agent;



@Repository
public interface AgentRepository extends JpaRepository<Agent,Long>{
	List<Agent> findByAgtnameLikeOrderByIDDesc(String agtname);
	Optional<Agent> findByAgtId(String agtId);
	Long countAllByAgtId(String agtId);

}
