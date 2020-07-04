import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("io.gitlab.arturbosch.detekt") version "1.1.1"
    id("jacoco")
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

group = "com.jianglianein"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly: Configuration by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Redis
    implementation("org.springframework.data:spring-data-redis")
    implementation("redis.clients:jedis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // GraphQL
    implementation("com.graphql-java-kickstart:graphql-java-tools:5.4.1")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:5.3.1")
    implementation("com.graphql-java-kickstart:graphiql-spring-boot-starter:5.3.1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.7.6")

//    // Spring cloud
//    implementation("org.springframework.cloud:spring-cloud-starter-hystrix")

    // DevOps
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.assertj:assertj-core:3.14.0")
}

detekt {
    toolVersion = "1.1.1"
    input = files("$projectDir/src/main/kotlin")
    config = files("$projectDir/src/main/resources/stylecheck.yml")
}

jacoco {
    toolVersion = "0.8.2"
    reportsDir = file("$buildDir/jacocoReport")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Detekt> {
    exclude(".*/resources/.*", ".*/build/.*")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.register("download") {
    configurations.testCompile.get().files
    println("Downloaded all dependencies")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"

            limit {
                excludes = listOf("**Application*", "**config.*", "**dto*", "**entity*", "**resource*", "**service*", "**repository*")
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = BigDecimal(0.85)
            }
        }
    }
}

tasks.build {
    finalizedBy(tasks.jacocoTestCoverageVerification, tasks.jacocoTestReport)
}

tasks.bootRun {
    val env = System.getenv("SPRING_PROFILES_ACTIVE") ?: "local"
    val peopleServiceUrl = System.getenv("peopleservice_url") ?: "http://localhost:8000"
    val projectServiceUrl = System.getenv("scrumprojectservice_url") ?: "http://localhost:8001"
    val messageServiceUrl = System.getenv("messageservice_url") ?: "http://localhost:8002"
    val pictureBedUrl = System.getenv("picture_bed_url") ?: "http://localhost:8003"
    val pictureBedToken = System.getenv("picture_bed_token") ?: ""
    val redisHost = System.getenv("redis_host") ?: ""
    val redisPort = System.getenv("redis_port") ?: ""
    val redisPassword = System.getenv("redis_password") ?: ""
    val serverPort = System.getenv("server_port") ?: ""

    System.out.println(env)
    systemProperty("spring.profiles.active", env)
    systemProperty("microservice.peopleservice.url", peopleServiceUrl)
    systemProperty("microservice.scrumprojectservice.url", projectServiceUrl)
    systemProperty("microservice.messageservice.url", messageServiceUrl)
    systemProperty("otherservice.picture-bed.url", pictureBedUrl)
    systemProperty("otherservice.picture-bed.token", pictureBedToken)
    systemProperty("spring.redis.host", redisHost)
    systemProperty("spring.redis.port", redisPort)
    systemProperty("spring.redis.password", redisPassword)
    systemProperty("server.port", serverPort)
}
