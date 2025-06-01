package com.example.demo.jackson.deserialization.spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.jackson.deserialization.basic.data.MemberNoArgs;
import com.example.demo.jackson.deserialization.spring.data.MemberAllArgs;
import com.example.demo.jackson.deserialization.spring.data.MemberNoArgsGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
public class SpringbootDeserializationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.jackson.property-naming-strategy", () -> "SNAKE_CASE");
    }

    @Test
    @DisplayName("Spring boot에서는 Object Member를 쓸 때는 전체 생성자만 있어도 직렬화에 성공한다.")
    public void testAllArgsDeserializationTest() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";

        MemberAllArgs member = objectMapper.readValue(memberJson, MemberAllArgs.class);
        System.out.println(member); // MemberAllArgsFinalField(id=1, name=홍길동, age=20, email=gildong@naver.com)
    }

    @Test
    @DisplayName("Spring boot에서 Object Member를 쓸 때는 기본 생성자와 Getter가 있으면 직렬화에 성공한다.")
    public void testNoArgsGetter() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";

        MemberNoArgsGetter member = objectMapper.readValue(memberJson, MemberNoArgsGetter.class);
        System.out.println(member); // MemberNoArgsGetter(id=1, name=홍길동, age=20, email=gildong@naver.com)
    }

    @Test
    @DisplayName("Spring boot에서 Object Member를 쓸 때는 기본 생성자만 있으면 직렬화 시 값이 들어오지 않는다.")
    public void testNoArgs() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";

        MemberNoArgs member = objectMapper.readValue(memberJson, MemberNoArgs.class);
        System.out.println(member); // MemberNoArgs(id=0, name=null, age=0, email=null)
    }
}
