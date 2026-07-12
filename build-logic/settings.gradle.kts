plugins {
    id("com.autonomousapps.build-health") version "3.16.0"
    id("org.jetbrains.kotlin.jvm") version "2.3.21" apply false
}

rootProject.name = "build-logic"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
