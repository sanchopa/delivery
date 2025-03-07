plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "delivery"
include("api")
project(":api").projectDir = file("modules/api")
include("core")
project(":core").projectDir = file("modules/core")
include("infrastructure")
project(":infrastructure").projectDir = file("modules/infrastructure")