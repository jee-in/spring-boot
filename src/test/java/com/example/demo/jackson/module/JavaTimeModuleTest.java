package com.example.demo.jackson.module;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JavaTimeModuleTest {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    @DisplayName("jackson jsr310의 JavaTimeModule을 추가하면 시간 객체를 직렬화할 수 있다.")
    void dateSerializationTest() throws IOException {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = objectMapper.writeValueAsString(LocalDateTime.now());
        System.out.println(json); // "2025-05-23T17:40:20.99018"
    }

    @Test
    @DisplayName("jackson jsr310의 JavaTimeModule을 추가하면 시간 JSON을 역직렬화할 수 있다.")
    void dateDeserializationTest() throws IOException {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        LocalDateTime dateTime = objectMapper.readValue("\"2025-05-23T17:40:20.99018\"", LocalDateTime.class);
        System.out.println(dateTime);
    }
}
