package com.example.demo.jackson.serialization.collection;

import com.example.demo.jackson.serialization.collection.data.MemberGetter;
import com.example.demo.jackson.serialization.collection.data.ReferencingMemberGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("List 컬렉션의 직렬화 테스트")
public class ListSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final long id = 1L;
    private final String name = "홍길동";
    private final int age = 20;
    private final String email = "gildong@korea.com";
    private final String password = "123456";
    private final String address = "서울특별시";

    @Test
    @DisplayName("객체 List의 직렬화는 성공한다.")
    void testListSerialization() throws JsonProcessingException {
        MemberGetter member = new MemberGetter();

        String listString = objectMapper.writeValueAsString(List.of(member, member));
        System.out.println(listString);
    }

    @Test
    @DisplayName("클래스 멤버변수의 타입이 참조 타입이어도 객체 List의 직렬화는 성공한다.")
    void testListOfReferencingObjectSerialization() throws JsonProcessingException {
        ReferencingMemberGetter member = new ReferencingMemberGetter();

        String json = objectMapper.writeValueAsString(List.of(member, member));
        System.out.println(json);
    }
}
