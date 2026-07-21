plugins {
    id("com.autonomousapps.build-health") version "3.17.0"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    kotlin("jvm") version "2.4.10" apply false
}

rootProject.name = "irrational"
includeBuild("build-logic")
include("core", "kotlin", "report-aggregation")
