plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.version.catalog.update)
}

repositories {
    mavenCentral()
}

configurations.configureEach {
    resolutionStrategy.componentSelection.all {
        if (candidate.version.endsWith("-SNAPSHOT", ignoreCase = true)) {
            reject("SNAPSHOT version rejected for ${candidate.group}:${candidate.module}:${candidate.version}")
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
    yaml {
        target("**/*.yaml")
        jackson().yamlFeature("MINIMIZE_QUOTES", true)
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    flexmark {
        target("**/*.md")
        flexmark("0.64.8")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

versionCatalogUpdate {
    pin {
        versions = setOf("cyclonedx")
    }
}
