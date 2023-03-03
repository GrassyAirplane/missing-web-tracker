plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(projects.core)

    implementation("org.springframework:spring-web:6.0.5")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {

    assemble {
        // Run the shadowJar task by default
        dependsOn(shadowJar)
    }

}