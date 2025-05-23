package com.example.demo.springboot.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "spring.config.import=file:./test-config.properties"
})
@ActiveProfiles("config-test")
public class ExternalPropertyTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Value("${my-secret-key}")
    String mySecretKey;

    @Test
    void readFromExternalConfig() {
        Object propertyOfYml =  environment.getPropertySources()
            .stream()
            .filter(source -> source.getName().equals("Config resource 'file [test-config.yml]' via location 'file:./test-config.yml'"))
            .findFirst()
            .get()
            .getProperty("my-secret-key");

        System.out.println(propertyOfYml); // 12345678

        Object propertyOfProperties =  environment.getPropertySources()
            .stream()
            .filter(source -> source.getName().equals("Config resource 'file [test-config.properties]' via location 'file:./test-config.properties'"))
            .findFirst()
            .get()
            .getProperty("my-secret-key");

        System.out.println(propertyOfProperties); // abcdefgh

        System.out.println(mySecretKey);    // abcdefgh
    }

}
