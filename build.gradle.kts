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
        gradleVersion = "9.7.1"
        distributionType = DistributionType.ALL
        distributionSha256Sum = "92c1a136d76b5017732a66d2e0a648ebff00dd3687d8bff0d0047a1bd904fdf2"
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
        versions = setOf("kotlin", "groovy", "assertj-core")
    }
}
