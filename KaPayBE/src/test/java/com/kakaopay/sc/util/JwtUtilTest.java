package com.kakaopay.sc.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @DisplayName("1. 토큰 생성 후 검증")
    @Test
    void test_1(){
        String agtId = "user1";
        String agtName = "사용자1";

        String token = jwtUtil.createToken(agtId, agtName);

        System.out.println("Token : " + token);

        assertThat(jwtUtil.decodeToken(token).getClaim("agtName").asString()).isEqualTo(agtName);
    }
}
