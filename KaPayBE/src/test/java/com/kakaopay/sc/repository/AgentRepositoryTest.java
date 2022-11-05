package com.kakaopay.sc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kakaopay.sc.entity.Agent;
import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.model.Header;
import com.kakaopay.sc.service.AgentService;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AgentRepositoryTest {
    @Autowired
    AgentRepository agentRepository;
    
    @Autowired
    QnaRepository qnaRepository;
    
    @Autowired
    AgentService agtService;
    
    @Autowired
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("1. 상담사 데이터 생성하기")
    @Test
    void test_1(){
        String encPassword = passwordEncoder.encode("test_password");
        Agent agt = new Agent();
        agt.setAgtId("test_user");
        agt.setAgtPw(encPassword);
        agt.setAgtname("test_name");
        Agent savedAgt = agentRepository.save(agt);
        assertThat(agt.getAgtId()).isEqualTo(savedAgt.getAgtId());
    }
    @DisplayName("2. 상담사 검색 후 비밀번호 비교")
    @Test    
    void test_2(){
        String agtId = "test_user";
        String agtPw = "test_password";
        UserDetails user = agtService.loadUserByUsername(agtId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, agtPw);
        authenticationManager.authenticate(authenticationToken);

        assertThat(authenticationToken.getCredentials()).isEqualTo(agtPw);

        System.out.println("getCredentials: " + authenticationToken.getCredentials());
        System.out.println("agtPw: " + agtPw);
    }
    @DisplayName("3. 상담사 아이디로 정보 검색")
    @Test    
    void test_3(){
        String agtId = "test_user";
        Agent agt = agtService.findAgentById(agtId);
        
        System.out.println(agt.getAgtId());
        System.out.println(agt.getAgtname());
        

    }    


}
