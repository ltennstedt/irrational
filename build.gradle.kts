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
        gradleVersion = "9.8.1"
        distributionType = DistributionType.ALL
        distributionSha256Sum = "5ceda8d2425eb2af196dec31b6fc1f38af2fb638fd621f86fa685805f2262224"
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
