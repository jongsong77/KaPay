package com.kakaopay.sc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.sc.entity.Cust;



@Repository
public interface CustRepository extends JpaRepository<Cust,Long>{

}
