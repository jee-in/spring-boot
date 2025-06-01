package com.example.demo.jackson.deserialization.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.demo.jackson.deserialization.basic.data.MemberAllArgsSetter;
import com.example.demo.jackson.deserialization.basic.data.MemberJsonCreator;
import com.example.demo.jackson.deserialization.basic.data.MemberOnlyJsonCreator;
import com.example.demo.jackson.deserialization.basic.data.MemberSetter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BasicDeserializationTest {

    @Test
    @DisplayName("클래스에 기본 생성자와 setter가 있으면 직렬화에 성공한다.")
    public void testClassWithSetter() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        MemberSetter member = objectMapper.readValue(memberJson, MemberSetter.class);
        assertThat(member)
            .isNotNull()
            .isInstanceOf(MemberSetter.class);
        System.out.println(member); // MemberSetter(id=1, name=홍길동, age=20, email=gildong@naver.com)
    }

    @Test
    @DisplayName("클래스에 전체 생성자와 setter만 있으면 직렬화에 실패한다.")
    public void testClassWithAllargsAndSetter() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Throwable thrown = catchThrowable(() -> objectMapper.readValue(memberJson, MemberAllArgsSetter.class));
        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class)
            .isInstanceOf(JsonProcessingException.class);

        System.out.println(thrown.getClass());      // class com.fasterxml.jackson.databind.exc.InvalidDefinitionException
        System.out.println(thrown.getMessage());    // Cannot construct instance of `com.example.demo.data.MemberAllArgsSetter`
                                                    // (no Creators, like default constructor, exist): cannot deserialize from Object value
                                                    // (no delegate- or property-based Creator) at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 2]
    }

    @Test
    @DisplayName("클래스에 전체 생성자가 있고 그 생성자에 @JsonCreator, @JsonProperty 애너테이션이 있으면 직렬화에 성공한다.")
    public void testClassWithJsonCreator() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        MemberJsonCreator member = objectMapper.readValue(memberJson, MemberJsonCreator.class);
        assertThat(member)
            .isNotNull()
            .isInstanceOf(MemberJsonCreator.class);
        System.out.println(member); // MemberJsonCreator(id=1, name=홍길동, age=20, email=gildong@naver.com)
    }

    @Test
    @DisplayName("클래스에 전체 생성자가 있고 그 생성자에 @JsonCreator만 있고 @JsonProperty 애너테이션이 있으면 직렬화에 실패한다.")
    public void testClassWithOnlyJsonCreator() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Throwable thrown = catchThrowable(() -> objectMapper.readValue(memberJson, MemberOnlyJsonCreator.class));
        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class);

        System.out.println(thrown.getClass());      // class com.fasterxml.jackson.databind.exc.InvalidDefinitionException
        System.out.println(thrown.getMessage());    // Invalid type definition for type `com.example.demo.data.MemberOnlyJsonCreator`:
                                                    // More than one argument (#0 and #1) left as delegating for Creator
                                                    // [constructor for `com.example.demo.data.MemberOnlyJsonCreator` (4 args), annotations:
                                                    // {interface com.fasterxml.jackson.annotation.JsonCreator=@com.fasterxml.jackson.annotation.JsonCreator(mode=DEFAULT)}: only one allowed
                                                    // at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 1]
    }

    @Test
    @DisplayName("JSON 형식이 잘못된 경우 역직렬화에 실패한다.")
    public void testClassWithWrongJSON() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":}";
        ObjectMapper objectMapper = new ObjectMapper();

        Throwable thrown = catchThrowable(() -> objectMapper.readValue(memberJson, MemberSetter.class));
        assertThat(thrown)
            .isInstanceOf(JsonParseException.class)
            .isNotInstanceOf(InvalidDefinitionException.class);

        System.out.println(thrown.getClass());      // class com.fasterxml.jackson.core.JsonParseException
        System.out.println(thrown.getMessage());    // Unexpected character ('}' (code 125)): expected a valid value
                                                    // (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
                                                    // at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 39]
    }

    @Test
    @DisplayName("JSON 필드와 클래스의 필드가 일치하지 않으면 역직렬화에 실패한다..")
    public void testClassWithMisMatch() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"address\":\"gildong@naver.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Throwable thrown = catchThrowable(() -> objectMapper.readValue(memberJson, MemberSetter.class));
        assertThat(thrown)
            .isInstanceOf(UnrecognizedPropertyException.class)
            .isInstanceOf(DatabindException.class)  // class com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
            .isNotInstanceOf(InvalidDefinitionException.class); // Unrecognized field "address" (class com.example.demo.data.MemberSetter),
                                                                // not marked as ignorable (4 known properties: "id", "email", "age", "name"])
                                                                // at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 42]
                                                                // (through reference chain: com.example.demo.data.MemberSetter["address"])
    }

    @Test
    @DisplayName("JSON 필드에 추가적인 필드가 있으면 역직렬화에 실패한다..")
    public void testJsonWithExtraField() throws JsonProcessingException {
        String memberJson = "{\"id\":1,\"name\":\"홍길동\",\"age\":20,\"email\":\"gildong@naver.com\",\"address\":\"gildong@naver.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Throwable thrown = catchThrowable(() -> objectMapper.readValue(memberJson, MemberSetter.class));
        assertThat(thrown)
            .isInstanceOf(UnrecognizedPropertyException.class)
            .isInstanceOf(DatabindException.class)
            .isNotInstanceOf(InvalidDefinitionException.class);
    }
}
