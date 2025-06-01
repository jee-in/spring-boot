package com.example.demo.jackson.serialization.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.demo.jackson.serialization.basic.data.caution.MemberOtherGetter;
import com.example.demo.jackson.serialization.basic.data.caution.MemberOtherGetterExclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.PathNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("직렬화 주의점 테스트")
public class BasicSerializationCautionTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("get 으로 시작하는 메서드가 있으면 해당 메서드도 직렬화가 된다.")
    void testMethodStartsWithGet() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(new MemberOtherGetter());

        assertThat((String) JsonPath.read(json, "$.message")).isNotEmpty();
        System.out.println(json);
    }

    @Test
    @DisplayName("Json.exclude로 get 으로 시작하는 메서드의 직렬화를 막을 수 있다.")
    void testExcludingMethodStartsWithGet() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(new MemberOtherGetterExclude());

        Throwable thrown = catchThrowable(() -> JsonPath.read(json, "$.message"));
        assertThat(thrown)
            .isInstanceOf(PathNotFoundException.class)
            .isInstanceOf(InvalidPathException.class)
            .isInstanceOf(JsonPathException.class);

        System.out.println(thrown.getClass());
        System.out.println(thrown.getMessage());
        System.out.println(json);
    }

    @Test
    @DisplayName("")
    void testExcludeGetters() throws JsonProcessingException {
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String json = objectMapper.writeValueAsString(new MemberOtherGetter());

        Throwable thrown = catchThrowable(() -> JsonPath.read(json, "$.message"));
        assertThat(thrown)
            .isInstanceOf(PathNotFoundException.class)
            .isInstanceOf(InvalidPathException.class)
            .isInstanceOf(JsonPathException.class);

        System.out.println(thrown.getClass());
        System.out.println(thrown.getMessage());
        System.out.println(json);
    }
}

