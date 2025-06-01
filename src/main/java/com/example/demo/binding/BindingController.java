package com.example.demo.binding;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BindingController {
    @GetMapping("/test")
    public TestResponse testOnGet(String params) {
        return new TestResponse(params);
    }

    // @RequestBody로 들어오는 JSON 필드는 이미 ObjectMapper를 통해 역직렬화된 이후입니다.
    @PostMapping("/test")
    public TestResponse testOnPost(@RequestBody TestResponse content) {
        System.out.println("message: " + content.message);
        return new TestResponse(content.message);
    }

    @Data
    private static class TestResponse {
        private final String message;
    }
}
