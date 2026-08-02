import nl.littlerobots.vcu.plugin.resolver.VersionSelectors
import nl.littlerobots.vcu.plugin.versionSelector

plugins {
    id("base-conventions")
    alias(libs.plugins.version.catalog.update)
}

tasks {
    check {
        dependsOn(buildHealth)
    }
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
    versionSelector {
        val isKotlinPlugin =
            it.candidate.group == "org.jetbrains.kotlin.jvm" &&
                it.candidate.module == "org.jetbrains.kotlin.jvm.gradle.plugin"
        (isKotlinPlugin && it.candidate.version.startsWith("2.2.")) ||
            (!isKotlinPlugin && VersionSelectors.STABLE.select(it))
    }
    pin {
        versions = setOf("spotless")
    }
}
