rootProject.name = "delivery"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        id("org.springframework.boot") version "3.4.4"
        id("io.spring.dependency-management") version "1.1.7"
        id("org.jetbrains.kotlin.jvm")
        id("org.jetbrains.kotlin.plugin.spring")
        id("org.jetbrains.kotlin.plugin.jpa") version "2.1.20"
        id("org.flywaydb.flyway") version "11.4.0"
        id("org.jetbrains.kotlin.kapt")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("api")
project(":api").projectDir = file("modules/api")
include("core")
project(":core").projectDir = file("modules/core")
include("infrastructure")
project(":infrastructure").projectDir = file("modules/infrastructure")