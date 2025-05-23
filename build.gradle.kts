plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "4.0.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
    implementation("net.logstash.logback:logstash-logback-encoder:8.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Copy>("copyTestReport") {
    dependsOn(tasks.named("test"))
    from(layout.buildDirectory.dir("reports/tests/test"))
    into("docs/test-report")
}

tasks.named<org.asciidoctor.gradle.jvm.AsciidoctorTask>("asciidoctor") {
    attributes(
        mapOf(
            "source-highlighter" to "highlight",
        )
    )
}

tasks.register<Copy>("copyAsciiDoc") {
    dependsOn(tasks.named("asciidoctor"))
    from(layout.buildDirectory.dir("docs/asciidoc"))
    into("docs/post")
}
