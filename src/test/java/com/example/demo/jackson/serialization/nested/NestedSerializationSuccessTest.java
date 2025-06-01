package com.example.demo.jackson.serialization.nested;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.jackson.serialization.nested.data.NestedMemberGetter;
import com.example.demo.jackson.serialization.nested.data.NestedMemberPublic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("중첩 클래스 직렬화 테스트")
public class NestedSerializationSuccessTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("내부 클래스가 public이고 getter가 있으면 직렬화에 성공한다.")
    void test() throws JsonProcessingException {
        NestedMemberGetter obj = new NestedMemberGetter();
        String json = objectMapper.writeValueAsString(obj);

        assertThat(((Integer) JsonPath.read(json, "$.id")).longValue()).isEqualTo(obj.getId());
        assertThat((String) JsonPath.read(json, "$.name")).isEqualTo(obj.getName());
        assertThat((Integer) JsonPath.read(json, "$.age")).isEqualTo(obj.getAge());
        assertThat((String) JsonPath.read(json, "$.email")).isEqualTo(obj.getEmail());
        assertThat(((Integer) JsonPath.read(json, "$.order.id")).longValue()).isEqualTo(obj.getOrder().getId());
        assertThat((String) JsonPath.read(json, "$.order.name")).isEqualTo(obj.getOrder().getName());
        System.out.println(json);
    }

    @Test
    @DisplayName("내부 클래스가 public이고 멤버 변수가 public이면 직렬화에 성공한다.")
    void testPublicFiels() throws JsonProcessingException {
        NestedMemberPublic obj = new NestedMemberPublic();
        String json = objectMapper.writeValueAsString(obj);

        assertThat(((Integer) JsonPath.read(json, "$.id")).longValue()).isEqualTo(obj.getId());
        assertThat((String) JsonPath.read(json, "$.name")).isEqualTo(obj.getName());
        assertThat((Integer) JsonPath.read(json, "$.age")).isEqualTo(obj.getAge());
        assertThat((String) JsonPath.read(json, "$.email")).isEqualTo(obj.getEmail());
        assertThat(((Integer) JsonPath.read(json, "$.order.id")).longValue()).isEqualTo(obj.getOrder().id);
        assertThat((String) JsonPath.read(json, "$.order.name")).isEqualTo(obj.getOrder().name);
        System.out.println(json);
    }
}
