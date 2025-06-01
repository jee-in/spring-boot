package com.example.demo.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class BeanTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    @DisplayName("스캔되고 등록된 빈의 목록을 출력한다.")
    void contextLoads() {
        Map<String, List<BeanInfo>> beanPackageMap = new HashMap<String, List<BeanInfo>>();

        String[] beans = applicationContext.getBeanDefinitionNames();
        System.out.println("===== Registered Beans =====");
        for (String beanName : beans) {

            Object bean = applicationContext.getBean(beanName);
            String beanClassName = bean.getClass().getName();

            int lastDotIdx = beanClassName.lastIndexOf(".");

            String className = beanClassName.substring(lastDotIdx + 1);
            String fullPackageName = beanClassName.substring(0, lastDotIdx);

            int lastDotIdxOfFullPackageName = fullPackageName.lastIndexOf(".");

            String superPackageName = fullPackageName.substring(0, lastDotIdxOfFullPackageName);
            String subPackageName = fullPackageName.substring(lastDotIdxOfFullPackageName + 1);

            List<BeanInfo> beanInfos = beanPackageMap.getOrDefault(superPackageName, new ArrayList<>());
            beanInfos.add(new BeanInfo(className, superPackageName, subPackageName, beanName));
            beanPackageMap.put(superPackageName, beanInfos);
        }

        for (Map.Entry<String, List<BeanInfo>> entry : beanPackageMap.entrySet()) {
            System.out.println(entry.getKey());
            for (BeanInfo beanInfo : entry.getValue()) {
                System.out.println(beanInfo);
            }
        }
    }

    class BeanInfo {
        String className;
        String superPackageName;
        String subPackageName;
        String beanName;

        public BeanInfo(String className, String superPackageName, String subPackageName, String beanName) {
            this.className = className;
            this.superPackageName = superPackageName;
            this.subPackageName = subPackageName;
            this.beanName = beanName;
        }

        @Override
        public String toString() {
            return "  \\ " + subPackageName + " > " + className;
        }
    }
}

/**
 ===== Registered Beans =====
 org.springframework.boot.validation
 \ beanvalidation > MethodValidationExcludeFilter$$Lambda$962/0x000000e001336d38
 org.springframework.boot.sql.init
 \ dependency > DatabaseInitializationDependencyConfigurer$DependsOnDatabaseInitializationPostProcessor
 org.springframework.test.context
 \ support > DynamicPropertyRegistrarBeanInitializer
 org.springframework.boot.autoconfigure.sql
 \ init > SqlInitializationAutoConfiguration
 \ init > SqlInitializationProperties
 com.example.demo
 \ service > MyService
 org.springframework.scheduling
 \ concurrent > ThreadPoolTaskExecutor
 org.springframework.boot.test.mock
 \ mockito > MockitoPostProcessor$SpyPostProcessor
 \ mockito > MockitoPostProcessor
 org.springframework.aop.framework
 \ autoproxy > InfrastructureAdvisorAutoProxyCreator
 org.springframework.boot
 \ autoconfigure > AutoConfigurationPackages$BasePackages
 \ availability > ApplicationAvailabilityBean
 \ jackson > JsonMixinModuleEntries
 \ jackson > JsonMixinModule
 \ jackson > JsonComponentModule
 \ ssl > DefaultSslBundleRegistry
 \ task > ThreadPoolTaskExecutorBuilder
 \ task > SimpleAsyncTaskExecutorBuilder
 \ task > ThreadPoolTaskSchedulerBuilder
 \ task > SimpleAsyncTaskSchedulerBuilder
 org.springframework.boot.context
 \ properties > ConfigurationPropertiesBindingPostProcessor
 \ properties > ConfigurationPropertiesBinder
 \ properties > BoundConfigurationProperties
 org.springframework.beans.factory
 \ annotation > AutowiredAnnotationBeanPostProcessor
 com.example
 \ demo > DemoApplication
 org.springframework.boot.type
 \ classreading > ConcurrentReferenceCachingMetadataReaderFactory
 org.springframework.boot.autoconfigure
 \ context > PropertyPlaceholderAutoConfiguration
 \ aop > AopAutoConfiguration$ClassProxyingConfiguration
 \ aop > AopAutoConfiguration$ClassProxyingConfiguration$$Lambda$943/0x000000e001321b58
 \ aop > AopAutoConfiguration
 \ availability > ApplicationAvailabilityAutoConfiguration
 \ jackson > JacksonAutoConfiguration$JacksonMixinConfiguration
 \ jackson > JacksonAutoConfiguration
 \ context > ConfigurationPropertiesAutoConfiguration
 \ context > LifecycleAutoConfiguration
 \ context > LifecycleProperties
 \ info > ProjectInfoAutoConfiguration
 \ info > ProjectInfoProperties
 \ ssl > SslAutoConfiguration
 \ ssl > FileWatcher
 \ ssl > SslPropertiesBundleRegistrar
 \ ssl > SslProperties
 \ task > TaskExecutorConfigurations$ThreadPoolTaskExecutorBuilderConfiguration
 \ task > TaskExecutorConfigurations$SimpleAsyncTaskExecutorBuilderConfiguration
 \ task > TaskExecutorConfigurations$TaskExecutorConfiguration
 \ task > TaskExecutionAutoConfiguration
 \ task > TaskExecutionProperties
 \ task > TaskSchedulingConfigurations$ThreadPoolTaskSchedulerBuilderConfiguration
 \ task > TaskSchedulingConfigurations$SimpleAsyncTaskSchedulerBuilderConfiguration
 \ task > TaskSchedulingAutoConfiguration
 \ task > TaskSchedulingProperties
 org.springframework.context
 \ annotation > ConfigurationClassPostProcessor
 \ annotation > CommonAnnotationBeanPostProcessor
 \ event > EventListenerMethodProcessor
 \ event > DefaultEventListenerFactory
 \ support > PropertySourcesPlaceholderConfigurer
 \ support > DefaultLifecycleProcessor
 */
