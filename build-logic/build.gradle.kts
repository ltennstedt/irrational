import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.spotless.plugin)
    implementation(libs.licensee.plugin)
    implementation(libs.validate.poms.plugin)
    implementation(libs.cyclonedx.plugin)
    implementation(libs.spotbugs.plugin)
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            apiVersion = KotlinVersion.KOTLIN_2_3
            languageVersion = KotlinVersion.KOTLIN_2_3
            progressiveMode = true
            extraWarnings = true
        }
    }
    withType<Detekt>().configureEach {
        reports {
            html {
                required = providers
                    .environmentVariable("CI")
                    .map { !it.equals("true", ignoreCase = true) }
                    .orElse(false)
            }
            md {
                required = false
            }
            sarif {
                required = false
            }
            txt {
                required = false
            }
            xml {
                required = false
            }
        }
    }
    check {
        dependsOn(buildHealth)
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
        target("**/*.gradle.kts")
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    kotlin {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

detekt {
    toolVersion = "1.23.8"
    config.from(files("../config/detekt/detekt.yaml"))
    buildUponDefaultConfig = true
}
