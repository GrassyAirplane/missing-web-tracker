plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.4")
    implementation("org.spongepowered:configurate-jackson:4.1.2")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {

    assemble {
        // Run the shadowJar task by default
        dependsOn(shadowJar)
    }

    withType<Test> {
        useJUnitPlatform()
    }

}