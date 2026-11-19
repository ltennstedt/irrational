import org.gradle.api.tasks.wrapper.Wrapper.DistributionType

plugins {
    id("base-conventions")
    alias(libs.plugins.version.catalog.update)
}

tasks {
    check {
        dependsOn(buildHealth)
    }
    wrapper {
        gradleVersion = "9.9.2"
        distributionType = DistributionType.ALL
        distributionSha256Sum = "1ac85e9cbc2b091ad04e6c6d0f486f0a2a2fabd78f9f8719a0d5bcc0ce1b345b"
    }
    register("dependenciesAll") {
        description = "Runs task dependencies on all subprojects"
        group = "help"
        dependsOn(subprojects.map { "${it.path}:dependencies" })
    }
}

spotless {
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
        versions = setOf("jspecify", "kotlin", "groovy", "junit", "assertj", "spock")
    }
}
