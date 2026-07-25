plugins {
    alias(libs.plugins.spotless)
    `java-library`
    `jvm-test-suite`
    `jacoco-report-aggregation`
}

repositories {
    mavenCentral()
}

dependencies {
    jacocoAggregation(project(":core"))
    jacocoAggregation(project(":kotlin"))
    jacocoAggregation(project(":groovy"))
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

tasks {
    val isCi: Provider<Boolean> =
        providers
            .environmentVariable("CI")
            .map { it.equals("true", ignoreCase = true) }
            .orElse(false)
    val isNotCi = isCi.map { !it }
    withType<JacocoReport>().configureEach {
        reports {
            html.required = isNotCi
            xml.required = isCi
        }
    }
    check {
        dependsOn(testCodeCoverageReport)
    }
    register("localBuild") {
        description = "Convenience task for local development builds before committing and pushing"
        group = "build"
        dependsOn(spotlessApply, build)
        enabled = isNotCi.get()
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

spotless {
    kotlinGradle {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnitJupiter(libs.versions.junit.get())
        }
    }
}
