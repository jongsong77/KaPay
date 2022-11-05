package com.kakaopay.sc.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kakaopay.sc.entity.Qna;
import com.kakaopay.sc.model.Header;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AgentControllerTest {

    @Autowired
    AgentController agentController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(agentController).build();
    }

    @DisplayName("1. 로그인 실패 테스트")
    @Test
    void test_1() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agtId", "test_userr");
        jsonObject.put("agtPw", "test_passwordd");

        ResultActions result = mockMvc.perform(post("/agtLogin")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @DisplayName("2. 로그인 성공 테스트")
    @Test
    void test_2() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agtId", "test_user");
        jsonObject.put("agtPw", "test_password");

        ResultActions result = mockMvc.perform(post("/agtLogin")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
