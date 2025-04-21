import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.spotless)
    `java-library`
    alias(libs.plugins.kotlin)
    groovy
    jacoco
    checkstyle
    pmd
    alias(libs.plugins.spotbugs)
    alias(libs.plugins.detekt)
    codenarc
    `maven-publish`
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.dependency.license.report)
    `project-report`
    `build-dashboard`
}

group = "irrational"
version = "0.1.0-SNAPSHOT"

val isCi = System.getenv("CI") != null
val isNotCi = !isCi
if (isCi) {
    logger.lifecycle("Environment variable CI is set.")
    gradle.startParameter.apply {
        consoleOutput = ConsoleOutput.Plain
        showStacktrace = ShowStacktrace.ALWAYS
        warningMode = WarningMode.Fail
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    constraints {
        testRuntimeOnly(libs.byte.buddy) {
            version {
                strictly(
                    libs.versions.byte.buddy
                        .get(),
                )
            }
            because("version conflict")
        }
    }
    api(libs.jspecify)
    testImplementation(libs.kotlin.stdlib)
    testImplementation(libs.groovy)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testImplementation(libs.equalsverifier)
    testImplementation(libs.kotest.assertions.core)
    testRuntimeOnly(libs.junit.platform)
}

listOf("api", "implementation", "runtimeOnly", "testImplementation", "testRuntimeOnly").forEach {
    configurations.named(it) {
        resolutionStrategy {
            failOnNonReproducibleResolution()
            failOnVersionConflict()
        }
    }
}

spotless {
    kotlinGradle {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    java {
        palantirJavaFormat("2.83.0").formatJavadoc(true)
        forbidModuleImports()
        forbidWildcardImports()
        formatAnnotations()
        removeUnusedImports()
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    kotlin {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    groovy {
        greclipse()
        removeSemicolons()
        excludeJava()
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
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withJavadocJar()
    withSourcesJar()
}

jacoco.toolVersion = "0.8.14"

checkstyle {
    toolVersion = "12.3.1"
    isIgnoreFailures = false
}

pmd {
    toolVersion = "7.20.0"
    ruleSetFiles = files("config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

spotbugs {
    toolVersion = "4.9.8"
    excludeFilter = file("config/spotbugs/exclude-filter.xml")
    ignoreFailures = false
}

detekt.buildUponDefaultConfig = true

codenarc.toolVersion = "3.7.0-groovy-4.0"

licenseReport.allowedLicensesFile = file("config/dependency-license-report/allowed-licenses.json")

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = "irrational"
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
                        organization = "none"
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
            repositories {
                maven {
                    name = "github"
                    url = uri("https://maven.pkg.github.com/ltennstedt/irrational/")
                    credentials {
                        username = System.getenv("GITHUB_ACTOR")
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }
    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
        failFast = isCi
        finalizedBy(jacocoTestReport)
    }
    jacocoTestReport {
        dependsOn(test)
        reports.xml.required = isCi
    }
    withType<Checkstyle>().configureEach {
        reports {
            html.required = isNotCi
            xml.required = false
        }
    }
    withType<Pmd>().configureEach {
        reports.html.required = isNotCi
    }
    withType<Detekt>().configureEach {
        reports {
            html.required = isNotCi
            md.required = false
            sarif.required = isCi
            txt.required = false
            xml.required = false
        }
    }
    withType<CodeNarc>().configureEach {
        reports.html.required = isNotCi
    }
    check {
        dependsOn(buildHealth)
    }
    wrapper {
        gradleVersion = "9.2.1"
    }
}
