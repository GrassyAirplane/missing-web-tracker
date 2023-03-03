plugins {
    java
    idea
    `visual-studio`
}

group = "cabbage"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val javaVersion = 17
val charset: String = Charsets.UTF_8.name();

subprojects {

    apply<JavaPlugin>()
    apply<IdeaPlugin>()
    apply<VisualStudioPlugin>()

    group = rootProject.group
    version = rootProject.version

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))

    dependencies {
        implementation("javax.annotation:javax.annotation-api:1.3.2")
    }

    tasks {

        withType(JavaCompile::class) {
            options.release.set(javaVersion)
            options.isDeprecation = true
            options.encoding = charset
        }

        withType(Javadoc::class) {
            options.encoding = charset
        }

        withType(ProcessResources::class) {
            filteringCharset = charset
        }

    }

}