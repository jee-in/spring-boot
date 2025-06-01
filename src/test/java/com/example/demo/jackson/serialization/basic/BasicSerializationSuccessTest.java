package com.example.demo.jackson.serialization.basic;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.jackson.serialization.basic.data.success.MemberGetter;
import com.example.demo.jackson.serialization.basic.data.success.MemberPublic;
import com.example.demo.jackson.serialization.basic.data.success.ReferencingMemberGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("직렬화 성공 테스트")
public class BasicSerializationSuccessTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("클래스에 getter가 있으면 직렬화에 성공한다.")
    void testGetter() throws JsonProcessingException {
        MemberGetter obj = new MemberGetter();
        String json = objectMapper.writeValueAsString(obj);

        assertThat(((Integer) JsonPath.read(json, "$.id")).longValue()).isEqualTo(obj.getId());
        assertThat((String) JsonPath.read(json, "$.name")).isEqualTo(obj.getName());
        assertThat((Integer) JsonPath.read(json, "$.age")).isEqualTo(obj.getAge());
        assertThat((String) JsonPath.read(json, "$.email")).isEqualTo(obj.getEmail());
        System.out.println(json);
    }

    @Test
    @DisplayName("클래스 멤버 변수가 모두 public이면 getter가 없어도 직렬화에 성공한다.")
    void testPublicFields() throws JsonProcessingException {
        MemberPublic obj = new MemberPublic();
        String json = objectMapper.writeValueAsString(obj);

        assertThat(((Integer) JsonPath.read(json, "$.id")).longValue()).isEqualTo(obj.id);
        assertThat((String) JsonPath.read(json, "$.name")).isEqualTo(obj.name);
        assertThat((Integer) JsonPath.read(json, "$.age")).isEqualTo(obj.age);
        assertThat((String) JsonPath.read(json, "$.email")).isEqualTo(obj.email);
        System.out.println(json);
    }

    @Test
    @DisplayName("클래스 내에서 참조하고 있는 클래스에 getter가 있으면 직렬화에 성공한다.")
    void testReferencingClass() throws JsonProcessingException {
        ReferencingMemberGetter obj = new ReferencingMemberGetter();
        String json = objectMapper.writeValueAsString(obj);

        assertThat(((Integer) JsonPath.read(json, "$.id")).longValue()).isEqualTo(obj.getId());
        assertThat((String) JsonPath.read(json, "$.name")).isEqualTo(obj.getName());
        assertThat((Integer) JsonPath.read(json, "$.age")).isEqualTo(obj.getAge());
        assertThat((String) JsonPath.read(json, "$.email")).isEqualTo(obj.getEmail());
        assertThat((String) JsonPath.read(json, "$.memberDetail.password")).isEqualTo(obj.getMemberDetail().getPassword());
        assertThat((String) JsonPath.read(json, "$.memberDetail.address")).isEqualTo(obj.getMemberDetail().getAddress());

        System.out.println(json);
    }
}
