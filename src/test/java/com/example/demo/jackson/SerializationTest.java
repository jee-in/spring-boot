package com.example.demo.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.demo.data.MemberAllArgs;
import com.example.demo.data.MemberGetter;
import com.example.demo.data.MemberPartialGetter;
import com.example.demo.data.MemberPublic;
import com.example.demo.data.NestedMember;
import com.example.demo.data.NestedMember.SubscribingChannel;
import com.example.demo.data.Privacy;
import com.example.demo.data.PrivacyGetter;
import com.example.demo.data.ReferencingMember;
import com.example.demo.data.ReferencingMemberGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.jayway.jsonpath.JsonPath;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SerializationTest {

    @Test
    @DisplayName("클래스에 getter가 있으면 직렬화에 성공한다.")
    void testSuccess() throws JsonProcessingException {
        MemberGetter member = new MemberGetter(1L, "홍길동", 20, "gildong@naver.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String memberString = objectMapper.writeValueAsString(member);

        Number idNum = JsonPath.read(memberString, "$.id");
        long id = idNum.longValue();
        String name = JsonPath.read(memberString, "$.name");
        int age = JsonPath.read(memberString, "$.age");
        String email = JsonPath.read(memberString, "$.email");

        assertThat(id).isEqualTo(1L);
        assertThat(name).isEqualTo("홍길동");
        assertThat(age).isEqualTo(20);
        assertThat(email).isEqualTo("gildong@naver.com");
        System.out.println(memberString);
    }

    @Test
    @DisplayName("클래스 일부 필드에 대해서만 getter가 있으면 해당 필드만 직렬화된다.")
    void testPartialGetterSuccess() throws JsonProcessingException {
        MemberPartialGetter member = new MemberPartialGetter(1L, "홍길동", 20, "gildong@naver.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String memberString = objectMapper.writeValueAsString(member);

        assertThat(memberString).isNotEmpty();
        System.out.println(memberString); // {"email":"gildong@naver.com"}
    }

    @Test
    @DisplayName("클래스 멤버 변수가 모두 public이면 getter가 없어도 직렬화에 성공한다.")
    void testWithPublicFields() throws JsonProcessingException {
        MemberPublic member = new MemberPublic(1L, "홍길동", 20, "gildong@naver.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String memberString = objectMapper.writeValueAsString(member);

        Number idNum = JsonPath.read(memberString, "$.id");
        long id = idNum.longValue();
        String name = JsonPath.read(memberString, "$.name");
        int age = JsonPath.read(memberString, "$.age");
        String email = JsonPath.read(memberString, "$.email");

        assertThat(id).isEqualTo(1L);
        assertThat(name).isEqualTo("홍길동");
        assertThat(age).isEqualTo(20);
        assertThat(email).isEqualTo("gildong@naver.com");
        System.out.println(memberString); // {"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com"}
    }

    @Test
    @DisplayName("전체 생성자만 있으면 직렬화에 실패한다.")
    void testWithoutGetter() throws JsonProcessingException {
        MemberAllArgs member = new MemberAllArgs(1L, "홍길동", 20, "gildong@naver.com");

        ObjectMapper objectMapper = new ObjectMapper();
        Throwable thrown = catchThrowable(() -> objectMapper.writeValueAsString(member));

        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class)
            .isInstanceOf(JsonProcessingException.class);
        System.out.println(thrown.getClass());      // class com.fasterxml.jackson.databind.exc.InvalidDefinitionException
        System.out.println(thrown.getMessage());    // No serializer found for class com.example.demo.data.MemberAllArgs and
                                                    // no properties discovered to create BeanSerializer
                                                    // (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    @Test
    @DisplayName("클래스 내에서 참조하고 있는 클래스에 getter가 없으면 직렬화에 실패한다.")
    void testReferencingClassWithoutGetter() throws JsonProcessingException {
        ReferencingMember member = new ReferencingMember(1L, "홍길동", 20, "gildong@naver.com", new Privacy("abcd1234", "서울시"));

        ObjectMapper objectMapper = new ObjectMapper();
        Throwable thrown = catchThrowable(() -> objectMapper.writeValueAsString(member));

        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class)
            .isInstanceOf(JsonProcessingException.class);
        System.out.println(thrown.getClass());      // class com.fasterxml.jackson.databind.exc.InvalidDefinitionException
        System.out.println(thrown.getMessage());    // No serializer found for class com.example.demo.data.Privacy and
                                                    // no properties discovered to create BeanSerializer
                                                    // (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                                    // (through reference chain: com.example.demo.data.ReferencingMember["privacy"])
    }

    @Test
    @DisplayName("클래스 내에서 참조하고 있는 클래스에 getter가 있으면 직렬화에 성공한다.")
    void testReferencingClassWithGetter() throws JsonProcessingException {
        ReferencingMemberGetter member = new ReferencingMemberGetter(1L, "홍길동", 20, "gildong@naver.com", new PrivacyGetter("abcd1234", "서울시"));

        ObjectMapper objectMapper = new ObjectMapper();
        String memberString = objectMapper.writeValueAsString(member);

        Number idNum = JsonPath.read(memberString, "$.id");
        long id = idNum.longValue();
        String name = JsonPath.read(memberString, "$.name");
        int age = JsonPath.read(memberString, "$.age");
        String email = JsonPath.read(memberString, "$.email");

        assertThat(id).isEqualTo(1L);
        assertThat(name).isEqualTo("홍길동");
        assertThat(age).isEqualTo(20);
        assertThat(email).isEqualTo("gildong@naver.com");
        System.out.println(memberString); // {"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com","privacy":{"password":"abcd1234","address":"서울시"}}
    }

    @Test
    @DisplayName("객체 List의 직렬화는 성공한다.")
    void testListSerialization() throws JsonProcessingException {
        MemberGetter member = new MemberGetter(1L, "홍길동", 20, "gildong@naver.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String listString = objectMapper.writeValueAsString(List.of(member, member)); // [{"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com"},{"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com"}]
        System.out.println(listString); // [1,2,3,4,5]
    }

    @Test
    @DisplayName("클래스 멤버변수의 타입이 참조 타입이어도 객체 List의 직렬화는 성공한다.")
    void testListOfReferencingObjectSerialization() throws JsonProcessingException {
        ReferencingMemberGetter member = new ReferencingMemberGetter(1L, "홍길동", 20, "gildong@naver.com", new PrivacyGetter("abcd1234", "서울시"));

        ObjectMapper objectMapper = new ObjectMapper();
        String listString = objectMapper.writeValueAsString(List.of(member, member)); // [{"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com","privacy":{"password":"abcd1234","address":"서울시"}},{"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com","privacy":{"password":"abcd1234","address":"서울시"}}]
        System.out.println(listString); // [1,2,3,4,5]
    }

    @Test
    @DisplayName("중첩 객체에 getter가 있으면 중첩 객체의 직렬화에 성공한다.")
    void testNestedClass() throws JsonProcessingException {
        NestedMember member = new NestedMember(1L, "홍길동", 20, "gildong@naver.com", List.of(new NestedMember.SubscribingChannel("짱구티비", "짱구")));

        ObjectMapper objectMapper = new ObjectMapper();
        String listString = objectMapper.writeValueAsString(List.of(member, member));
        System.out.println(listString); // [{"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com","subscribingChannels":[{"title":"짱구티비","creator":"짱구"}]},{"id":1,"name":"홍길동","age":20,"email":"gildong@naver.com","subscribingChannels":[{"title":"짱구티비","creator":"짱구"}]}]
    }

    @Test
    @DisplayName("시간 관련 클래스는 직렬화에 실패한다. (jackson-datatype-jsr310 필요)")
    void testTimeRelatedClass() throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();

        ObjectMapper objectMapper = new ObjectMapper();
        Throwable thrown = catchThrowable(() -> objectMapper.writeValueAsString(now));

        assertThat(thrown)
            .isInstanceOf(InvalidDefinitionException.class)
            .isInstanceOf(JsonProcessingException.class);

        System.out.println(thrown.getClass());   // class com.fasterxml.jackson.databind.exc.InvalidDefinitionException
        System.out.println(thrown.getMessage()); // Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
    }
}
