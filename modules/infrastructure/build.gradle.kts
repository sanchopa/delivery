plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "2.1.20"
    id("org.springframework.boot") version "3.4.4"
    id("org.flywaydb.flyway") version "11.4.0"
}

dependencies {
    api(project(":api"))
    api(project(":core"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
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