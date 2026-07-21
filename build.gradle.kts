plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.version.catalog.update)
}

repositories {
    mavenCentral()
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
    register("localBuild") {
        description = "Convenience task for local development builds before committing and pushing"
        group = "other"
        dependsOn(spotlessApply, versionCatalogFormat, build)
        enabled =
            providers
                .environmentVariable("CI")
                .map { it.equals("true", ignoreCase = true) }
                .orElse(false)
                .map { !it }
                .get()
    }
}

spotless {
    kotlinGradle {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    yaml {
        target(".github/workflows/gradle.yaml", "config/detekt/detekt.yaml")
        jackson().yamlFeature("MINIMIZE_QUOTES", true)
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    flexmark {
        target(".github/**/*.md", "*.md")
        flexmark("0.64.8")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

versionCatalogUpdate {
    pin {
        versions = setOf("spotless")
    }
}
