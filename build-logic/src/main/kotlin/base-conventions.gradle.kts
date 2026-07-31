import gradle.kotlin.dsl.accessors._9da13f0ce2420b7aafce48b332d000a7.build
import gradle.kotlin.dsl.accessors._9da13f0ce2420b7aafce48b332d000a7.spotlessApply

plugins {
    id("com.diffplug.spotless")
}

repositories {
    mavenCentral()
}

tasks {
    register("localBuild") {
        description = "Convenience task for local development builds before committing and pushing"
        group = "build"
        dependsOn(spotlessApply, build)
        enabled = isNotCi.get()
    }
}

configurations.configureEach {
    resolutionStrategy {
        componentSelection.all {
            if (candidate.version.endsWith("-SNAPSHOT", ignoreCase = true)) {
                reject("SNAPSHOT version rejected for ${candidate.group}:${candidate.module}:${candidate.version}")
            }
        }
    }
}

dependencyLocking {
    lockAllConfigurations()
}

spotless {
    kotlinGradle {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}
