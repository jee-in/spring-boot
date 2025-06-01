package com.example.demo.jackson.module;

import com.example.demo.jackson.module.data.MemberNoArgs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class ParameterNamesModuleTest {

    private String json = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@korea.com\"}";

    @Test
    @DisplayName("Spring 의 ObjectMapper 를 이용하면 기본 생성자만으로 역직렬화할 수 있다.")
    void testSpringObjectMapper() throws Exception {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
            .modulesToInstall(ParameterNamesModule.class)
            .build();
        MemberNoArgs member = objectMapper.readValue(json, MemberNoArgs.class);
        System.out.println(member);
    }

    @Test
    @DisplayName("기본 ObjectMapper에 ParameterNamesModule 을 추가")
    void testPlainObjectMapper() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule());

        // 현재 실패
//        objectMapper.readValue(json, MemberNoArgs.class);
    }
}
