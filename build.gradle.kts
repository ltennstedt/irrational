import com.github.jk1.license.render.InventoryMarkdownReportRenderer
import com.github.jk1.license.render.ReportRenderer
import com.github.jk1.license.render.SimpleHtmlReportRenderer
import com.github.jk1.license.render.XmlReportRenderer
import com.github.spotbugs.snom.SpotBugsTask
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import java.nio.charset.StandardCharsets.UTF_8

plugins {
    alias(libs.plugins.spotless)
    `java-library`
    jacoco
    checkstyle
    pmd
    alias(libs.plugins.spotbugs)
    `maven-publish`
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.dependency.license.report)
    alias(libs.plugins.task.tree)
}

group = "io.github.irrational"
version = "0.1.0-SNAPSHOT"

val isCi = providers.environmentVariable("CI").isPresent
if (isCi) {
    logger.lifecycle("Environment variable CI is set.")
    gradle.startParameter.apply {
        consoleOutput = ConsoleOutput.Plain
        showStacktrace = ShowStacktrace.ALWAYS
        warningMode = WarningMode.Fail
    }
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.jspecify)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testImplementation(libs.equalsverifier)
    testRuntimeOnly(libs.junit.platform)
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
    java {
        palantirJavaFormat("2.86.0").formatJavadoc(true)
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
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withJavadocJar()
    withSourcesJar()
}

jacoco {
    toolVersion = "0.8.14"
}

checkstyle {
    toolVersion = "12.3.1"
    isIgnoreFailures = false
}

pmd {
    toolVersion = "7.21.0"
    ruleSetFiles = files("config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

spotbugs {
    toolVersion = "4.9.8"
    excludeFilter = file("config/spotbugs/exclude-filter.xml")
    ignoreFailures = false
}

licenseReport {
    allowedLicensesFile = file("config/dependency-license-report/allowed-licenses.json")
    renderers =
        if (isCi) {
            arrayOf<ReportRenderer>(XmlReportRenderer(), InventoryMarkdownReportRenderer())
        } else {
            arrayOf<ReportRenderer>(
                SimpleHtmlReportRenderer(),
            )
        }
}

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
    withType<JavaCompile>().configureEach {
        options.encoding = UTF_8.name()
    }
    withType<ProcessResources>().configureEach {
        filteringCharset = UTF_8.name()
    }
    withType<Test>().configureEach {
        useJUnitPlatform()
        failFast = isCi
        reports {
            html.required.set(!isCi)
            junitXml.required.set(isCi)
        }
        finalizedBy(jacocoTestReport)
    }
    jacocoTestReport {
        dependsOn(test)
        reports {
            html.required.set(!isCi)
            xml.required.set(isCi)
        }
    }
    withType<Checkstyle>().configureEach {
        reports {
            html.required.set(!isCi)
            xml.required.set(isCi)
        }
    }
    withType<Pmd>().configureEach {
        reports {
            html.required.set(!isCi)
            xml.required.set(isCi)
        }
    }
    withType<SpotBugsTask>().configureEach {
        val taskName = name
        reports {
            create("html") {
                required.set(!isCi)
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
    check {
        dependsOn(buildHealth, checkLicense)
    }
    wrapper {
        gradleVersion = "8.14.4"
        distributionType = ALL
    }
}
