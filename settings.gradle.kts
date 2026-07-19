plugins {
    id("com.autonomousapps.build-health") version "3.16.1"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "irrational"
includeBuild("build-logic")
include("core", "report-aggregation")
