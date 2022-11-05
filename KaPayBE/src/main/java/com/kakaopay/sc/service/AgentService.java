package com.kakaopay.sc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kakaopay.sc.entity.Agent;
import com.kakaopay.sc.repository.AgentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService implements UserDetailsService{
	
	private final AgentRepository agtRepository;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public Agent findAgentById(String agtId) {
		return agtRepository.findByAgtId(agtId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")); //NOT_FOUND
	}
	@Override
	public UserDetails loadUserByUsername(String agtId) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Agent agt = agtRepository.findByAgtId(agtId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")); //NOT_FOUND

        if (agt.getAgtId().equals(agtId)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(agt.getAgtId(), agt.getAgtPw(), authorities);
	}
	public String signUp(Agent agt) {
		Long cnt = agtRepository.countAllByAgtId(agt.getAgtId());
		if(cnt > 0) return "ALREADY_EXIST"; //ALREADY_EXIST
		else {
			String encPassword = passwordEncoder.encode(agt.getAgtPw());
			agt.setAgtPw(encPassword);
			Agent agent = agtRepository.save(agt); 
			if(agent.getAgtId() != null) return "SUCCESS"; //SUCCESS
			else return "CREATE_FAIL"; //FAIL
		}
	}
}
