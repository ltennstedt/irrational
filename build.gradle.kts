plugins {
    id("base-conventions")
    alias(libs.plugins.version.catalog.update)
}

tasks {
    named("localBuild") {
        dependsOn(versionCatalogFormat)
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
        versions = setOf("spotless")
    }
}
