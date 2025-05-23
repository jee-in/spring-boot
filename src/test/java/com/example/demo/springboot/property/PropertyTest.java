package com.example.demo.springboot.property;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

@SpringBootTest
public class PropertyTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Value("${test.property}")
    String testProperty;

    @Test
    void printAllPropertySources() {
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.stream().forEach(source -> System.out.println(source.getName()));

        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().equals("configurationProperties"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().equals("test"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().equals("Inlined Test Properties"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().equals("systemProperties"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().equals("systemEnvironment"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().equals("random"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().contains("application.properties"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().contains("application.yml"));
        assertThat(propertySources).anyMatch(propertySource -> propertySource.getName().contains("applicationInfo"));

        assertThat(testProperty)
            .isEqualTo(
                propertySources
                    .stream()
                    .filter(propertySource -> propertySource.getName().contains("application.properties"))
                    .findFirst().get().getProperty("test.property")
            );
        System.out.println("Override testProperty value: " + testProperty);

    }
}
