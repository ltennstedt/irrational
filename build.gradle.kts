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
        gradleVersion = "9.8.2"
        distributionType = DistributionType.ALL
        distributionSha256Sum = "a1bb7e9651ef09e4cd9a62d471564e0ee23b5757e5f21fd7358d7cfc21018fdf"
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
        versions = setOf("kotlin", "groovy")
    }
}
