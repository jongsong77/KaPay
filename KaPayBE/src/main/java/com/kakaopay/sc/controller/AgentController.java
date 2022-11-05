package com.kakaopay.sc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.sc.entity.Agent;
import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.model.Header;
import com.kakaopay.sc.model.SearchCondition;
import com.kakaopay.sc.service.AgentService;
import com.kakaopay.sc.service.QnaService;
import com.kakaopay.sc.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AgentController {
	
	private final AgentService agtService;
	private final QnaService qnaService;
	
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
	
//	@GetMapping("/agts")
//	public List<Agent> getAgts(@RequestParam(required = false, defaultValue = "") String name) {
//		
//		return agtService.getAgentService(name);
//	}
//    @PostMapping(value = "/agt")
//    public String createAgt(@RequestBody Agent agt){
//        return agtService.createAgtService(agt);
//    }
    @PostMapping(value ="/agtLogin") // 상담원 로그인
    public ResponseEntity<Map<String, Object>> agtLogin(@RequestBody Map<String, String> paramMap) {
    	String agtId= paramMap.get("agtId");
    	String agtPw= paramMap.get("agtPw");
        UserDetails loginUser = agtService.loadUserByUsername(agtId); //userId로 정보 가져오기

        Authentication authentication = authenticationManager.authenticate(     //가져온 정보와 입력한 비밀번호로 검증
                new UsernamePasswordAuthenticationToken(loginUser, agtPw)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);   // 검증 통과 후 authentication 세팅

        String accessToken = jwtUtil.createToken(loginUser.getUsername(), loginUser.getUsername());     //accessToken 생성
        Agent agt = agtService.findAgentById(agtId);
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", loginUser.getUsername());
        result.put("user_token", accessToken);
        result.put("user_role", loginUser.getAuthorities().stream().findFirst().get().getAuthority());
        result.put("user_name", agt.getAgtname());

        return ResponseEntity.ok(result);    	
    }
    @GetMapping("/qnaList") // 미완료 글 리스트
    public Header<List<Qna>> qnaList(@PageableDefault(sort = {"idx"}) Pageable pageable,
    	SearchCondition searchCondition,@RequestParam(required = false, defaultValue = "") String agtId) {
    	return qnaService.getQnaList(pageable,searchCondition);
    }
    @PostMapping(value="/assignQna") // 상담 배정
    public String assignQna(@RequestBody Qna qna) {
    	int res = qnaService.updateState(qna.getIdx(),qna.getState(),qna.getAgtId(),qna.getAgtName());
    	if(res == 1) return "SUCCESS"; // SUCCESS
    	else return "FAIL";  // FAIL
    	
    }
    @PostMapping(value="/replyQna") // 글답변
    public String replyQna(@RequestBody Qna qna) {
    	int res = qnaService.updateAgtAnswerAndState(qna.getIdx(),qna.getState(),qna.getAgtAnswer());
    	if(res == 1) return "SUCCESS"; // SUCCESS
    	else return "FAIL";  //FAIL
    }
    @PostMapping(value="/signUp") // 상담사생성
    public String signUp(@RequestBody Agent agt) {
    	String res = agtService.signUp(agt);
    	return res;
    	
    }    
}
