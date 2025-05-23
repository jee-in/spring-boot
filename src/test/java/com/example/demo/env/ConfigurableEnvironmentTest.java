package com.example.demo.env;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

@SpringBootTest
public class ConfigurableEnvironmentTest {

    @Autowired
    ConfigurableEnvironment environment;

    @Test
    @DisplayName("Spring Environment의 ActiveProfiles, DefaultProfiles, PropertySources를 출력한다.")
    void contextLoads() {
        System.out.println("\n===== Spring Environment Info =====");
        System.out.println(environment);

        // 1. Active Profiles
        System.out.println("▶ Active Profiles:");
        for (String profile : environment.getActiveProfiles()) {
            System.out.println("  - " + profile);
        }

        // 2. Default Profiles
        System.out.println("▶ Default Profiles:");
        for (String profile : environment.getDefaultProfiles()) {
            System.out.println("  - " + profile);
        }

        // 3. Property Sources
        System.out.println("▶ PropertySources:");
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            System.out.println("  - " + propertySource.getName());
        }
    }

/*
===== Spring Environment Info =====
ApplicationEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[ConfigurationPropertySourcesPropertySource {name='configurationProperties'}, MapPropertySource {name='test'}, MapPropertySource {name='Inlined Test Properties'}, PropertiesPropertySource {name='systemProperties'}, OriginAwareSystemEnvironmentPropertySource {name='systemEnvironment'}, RandomValuePropertySource {name='random'}, OriginTrackedMapPropertySource {name='Config resource 'class path resource [application.properties]' via location 'optional:classpath:/''}, OriginTrackedMapPropertySource {name='Config resource 'class path resource [application.yml]' via location 'optional:classpath:/''}, ApplicationInfoPropertySource {name='applicationInfo'}]}
▶ Active Profiles:
▶ Default Profiles:
  - default
▶ PropertySources:
  - configurationProperties
  - test
  - Inlined Test Properties
  - systemProperties
  - systemEnvironment
  - random
  - Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'
  - Config resource 'class path resource [application.yml]' via location 'optional:classpath:/'
  - applicationInfo
**/
}
