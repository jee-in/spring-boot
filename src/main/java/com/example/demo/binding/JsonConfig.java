package com.example.demo.binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JsonConfig {

    private final ObjectMapper objectMapper;

    public JsonConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(customJsonDeserializeModule());
        System.out.println("Custom module is registered to ObjectMapper");
    }

    private SimpleModule customJsonDeserializeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringStripJsonDeserializer());

        return module;
    }
}
