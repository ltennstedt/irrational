plugins {
    id("com.autonomousapps.build-health") version "3.18.0"
    kotlin("jvm") version "2.3.21" apply false
}

rootProject.name = "build-logic"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
