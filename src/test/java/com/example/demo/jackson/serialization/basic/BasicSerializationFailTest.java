package com.example.demo.jackson.serialization.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.demo.jackson.serialization.basic.data.fail.Member;
import com.example.demo.jackson.serialization.basic.data.fail.MemberAllArgs;
import com.example.demo.jackson.serialization.basic.data.fail.MemberPartialGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("직렬화 실패 테스트")
public class BasicSerializationFailTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("private 필드에 getter 가 없는 클래스는 직렬화에 실패한다.")
    void testPrivateNoGetter() throws JsonProcessingException {
        Member obj = new Member();
        Throwable thrown = catchThrowable(() -> objectMapper.writeValueAsString(obj));

        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class)
            .isInstanceOf(JsonMappingException.class)
            .isInstanceOf(DatabindException.class)
            .isInstanceOf(JsonProcessingException.class);
        System.out.println(thrown.getClass());
        System.out.println(thrown.getMessage());
    }


    @Test
    @DisplayName("전체 생성자만 있고 Getter가 없는 클래스는 직렬화에 실패한다.")
    void testAllArgs() throws JsonProcessingException {
        MemberAllArgs obj = new MemberAllArgs();
        Throwable thrown = catchThrowable(() -> objectMapper.writeValueAsString(obj));

        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class)
            .isInstanceOf(JsonMappingException.class)
            .isInstanceOf(DatabindException.class)
            .isInstanceOf(JsonProcessingException.class);
        System.out.println(thrown.getClass());
        System.out.println(thrown.getMessage());
    }

    @Test
    @DisplayName("클래스 일부 필드에 대해서만 getter가 있으면 해당 필드만 직렬화된다.")
    void testPartialGetterSuccess() throws JsonProcessingException {
        MemberPartialGetter member = new MemberPartialGetter();
        String memberString = objectMapper.writeValueAsString(member);

        assertThat(memberString).isNotEmpty();
        System.out.println(memberString); // {"email":"gildong@naver.com"}
    }
}
