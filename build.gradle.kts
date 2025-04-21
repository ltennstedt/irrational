import com.github.spotbugs.snom.SpotBugsTask
import org.cyclonedx.gradle.CyclonedxDirectTask
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType
import java.nio.charset.StandardCharsets

plugins {
    alias(libs.plugins.spotless)
    `java-library`
    jacoco
    checkstyle
    pmd
    alias(libs.plugins.spotbugs)
    alias(libs.plugins.validate.poms)
    alias(libs.plugins.cyclonedx)
    alias(libs.plugins.licensee)
    `maven-publish`
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.task.tree)
}

group = "io.github.irrational"
version = "0.1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    api(libs.jspecify)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.platform)
}

configurations.configureEach {
    resolutionStrategy.componentSelection.all {
        if (candidate.version.endsWith("-SNAPSHOT", ignoreCase = true)) {
            reject("SNAPSHOT version rejected for ${candidate.group}:${candidate.module}:${candidate.version}")
        }
    }
}

dependencyLocking { lockAllConfigurations() }

spotless {
    kotlinGradle {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    java {
        palantirJavaFormat("2.89.0").formatJavadoc(true)
        forbidModuleImports()
        forbidWildcardImports()
        formatAnnotations()
        removeUnusedImports()
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
    json {
        target("**/*.json")
        jackson()
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

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
    withJavadocJar()
    withSourcesJar()
}

jacoco { toolVersion = "0.8.14" }

checkstyle {
    toolVersion = "12.3.1"
    isIgnoreFailures = false
}

pmd {
    toolVersion = "7.22.0"
    ruleSetFiles = files("config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

spotbugs {
    toolVersion = "4.9.8"
    excludeFilter = file("config/spotbugs/exclude-filter.xml")
    ignoreFailures = false
}

licensee {
    allow("Apache-2.0")
}

publishing {
    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/ltennstedt/irrational/")
            credentials {
                username = providers.environmentVariable("GITHUB_ACTOR").orNull
                password = providers.environmentVariable("GITHUB_TOKEN").orNull
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks.named("cyclonedxDirectBom")) {
                classifier = "bom"
                extension = "json"
            }
            pom {
                artifactId = "irrational-core"
                name = artifactId
                description = "Mathematical library for the JVM"
                url = "https://github.com/ltennstedt/irrational"
                inceptionYear = "2025"
                licenses {
                    license {
                        name = "MIT"
                        url = "https://opensource.org/license/mit"
                    }
                }
                developers {
                    developer {
                        id = "ltennstedt"
                        name = "Lars Tennstedt"
                        email = "27898583+ltennstedt@users.noreply.github.com"
                        organizationUrl = "https://github.com/ltennstedt/"
                        roles = listOf("developer")
                        timezone = "Europe/Berlin"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/ltennstedt/irrational"
                    developerConnection = "scm:git:https://github.com/ltennstedt/irrational"
                    url = "https://github.com/ltennstedt/irrational"
                }
                issueManagement {
                    system = "GitHub Issues"
                    url = "https://github.com/ltennstedt/irrational/issues/"
                }
                ciManagement {
                    system = "GitHub Actions"
                    url = "https://github.com/ltennstedt/irrational/actions/"
                }
            }
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach { options.encoding = StandardCharsets.UTF_8.name() }
    withType<ProcessResources>().configureEach { filteringCharset = StandardCharsets.UTF_8.name() }
    val isCi: Provider<Boolean> =
        providers
            .environmentVariable("CI")
            .map { it.equals("true", ignoreCase = true) }
            .orElse(false)
    val isNotCi = isCi.map { !it }
    withType<Test>().configureEach {
        useJUnitPlatform()
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
        failFast = isCi.get()
        reports {
            html.required.set(isNotCi)
            junitXml.required.set(isCi)
        }
    }
    withType<JacocoReport>().configureEach {
        dependsOn(test)
        reports {
            html.required.set(isNotCi)
            xml.required.set(isCi)
        }
    }
    withType<Checkstyle>().configureEach {
        exclude("**/module-info.java")
        reports {
            html.required.set(isNotCi)
            xml.required.set(isCi)
        }
    }
    withType<Pmd>().configureEach {
        reports {
            html.required.set(isNotCi)
            xml.required.set(isCi)
        }
    }
    withType<SpotBugsTask>().configureEach {
        val taskName = name
        reports {
            create("html") {
                required.set(isNotCi)
                outputLocation.set(
                    layout.buildDirectory.file("reports/spotbugs/$taskName.html"),
                )
            }
            create("xml") {
                required.set(isCi)
                outputLocation.set(
                    layout.buildDirectory.file("reports/spotbugs/$taskName.xml"),
                )
            }
        }
    }
    withType<CyclonedxDirectTask>().configureEach {
        includeConfigs =
            listOf(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME, JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME)
        xmlOutput.unsetConvention()
    }
    withType<Wrapper>().configureEach {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
        distributionType = DistributionType.ALL
    }
    check { dependsOn(jacocoTestReport, buildHealth, licensee) }
}
