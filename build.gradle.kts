import nl.littlerobots.vcu.plugin.resolver.VersionSelectors
import nl.littlerobots.vcu.plugin.versionSelector
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
        gradleVersion = "9.7.0"
        distributionType = DistributionType.ALL
        distributionSha256Sum = "a9ecb5ac5c2ca40691e6527724d11d0b43b8c0a52825b77c09899f2a72d2d2bf"
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
    versionSelector {
        val isKotlinPlugin =
            it.candidate.group == "org.jetbrains.kotlin.jvm" &&
                it.candidate.module == "org.jetbrains.kotlin.jvm.gradle.plugin"
        (isKotlinPlugin && it.candidate.version.startsWith("2.2.")) ||
            (!isKotlinPlugin && VersionSelectors.STABLE.select(it))
    }
    pin {
        versions = setOf("spock")
    }
}
