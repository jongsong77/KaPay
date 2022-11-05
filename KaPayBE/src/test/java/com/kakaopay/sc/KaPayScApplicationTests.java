package com.kakaopay.sc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kakaopay.sc.repository.AgentRepository;

@DataJpaTest
class KaPayScApplicationTests {
	@Autowired
		AgentRepository agentRepository;
	

}
