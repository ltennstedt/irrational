plugins {
    id("com.autonomousapps.build-health") version "3.18.0"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    kotlin("jvm") version "2.2.21" apply false
}

rootProject.name = "irrational"
includeBuild("build-logic")
include("bom", "core", "kotlin", "groovy", "report-aggregation")
