import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.proto

plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "2.1.20"
    id("org.springframework.boot") version "3.4.4"
    id("org.flywaydb.flyway") version "11.4.0"
    id("com.google.protobuf") version "0.9.1"
}

dependencies {
    api(project(":api"))
    api(project(":core"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.grpc:grpc-netty:1.46.0")
    implementation("io.grpc:grpc-protobuf:1.53.0")
    implementation("io.grpc:grpc-stub:1.46.0")
    implementation("io.grpc:grpc-kotlin-stub:1.2.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.18.1")
    implementation("com.google.protobuf:protobuf-java:3.18.1")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
}

sourceSets {
    main {
        proto {
            srcDir("src/main/kotlin/org/example/adapters/grpc")
        }
    }
    val grpc = create("grpc") {
    }
    getByName("test") {
        compileClasspath += grpc.output
        runtimeClasspath += grpc.output
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.44.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.1:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

val dbUrl = project.properties["db_url"] as String?
val dbUser = project.properties["db_user"] as String?
val dbPassword = project.properties["db_password"] as String?

flyway {
    url = dbUrl
    user = dbUser
    password = dbPassword
    cleanDisabled = false
}

springBoot {
    buildInfo()
}