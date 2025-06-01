package com.example.demo.web.binding;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
class JsonConfigTest {
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeNode() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 로그한글깨짐 문제해결
            .alwaysDo(print())
            .build();
    }

    @Test
    void showRegisteredModules() {
        System.out.println("Registered modules:");
        objectMapper.getRegisteredModuleIds().forEach(id -> System.out.println(" - " + id));
    }

    @DisplayName("StringStripJsonDeserializer")
    @Nested
    class StringStripJsonDeserializer {
        @Test
        void whenValueIsNull() throws Exception {
            mockMvc.perform(post("/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"message\" : null}"));
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value(nullValue()));
        }

        @Test
        void whenValueIsEmpty() throws Exception {
            mockMvc.perform(post("/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"message\" : \"\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(nullValue()));
        }

        @Test
        void whenValueIsBlank() throws Exception {
            mockMvc.perform(post("/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"message\" : \"   \"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(nullValue()));
        }

        @Test
        void whenValueIsWhiteSpaceOfUnicode() throws Exception {
            mockMvc.perform(post("/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"message\" : \"\u2003 \u200A\"}"))  // strip() is better than trim()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(nullValue()));
        }

        @Test
        void whenValueIsNotTrimmed() throws Exception {
            mockMvc.perform(post("/test")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"message\" : \"   hello world    \"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("hello world"));
        }
    }
}

