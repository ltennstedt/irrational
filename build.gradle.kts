
import com.github.jk1.license.render.InventoryHtmlReportRenderer
import com.github.jk1.license.render.InventoryMarkdownReportRenderer
import com.github.jk1.license.render.ReportRenderer
import com.github.jk1.license.render.XmlReportRenderer
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
    alias(libs.plugins.cyclonedx)
    `maven-publish`
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.dependency.license.report)
    alias(libs.plugins.task.tree)
}

group = "io.github.irrational"
version = "0.1.0-SNAPSHOT"

val isCi: Provider<Boolean> =
    providers
        .environmentVariable("CI")
        .map { it.equals("true", ignoreCase = true) }
        .orElse(false)

repositories {
    mavenCentral()
}

dependencies {
    api(libs.jspecify)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.platform)
}

dependencyLocking {
    lockAllConfigurations()
}

spotless {
    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    java {
        palantirJavaFormat(
            libs.versions.palantir.java.format
                .get(),
        ).formatJavadoc(true)
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
        flexmark(libs.versions.flexmark.get())
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
    toolVersion = libs.versions.jacoco.get()
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    isIgnoreFailures = false
}

pmd {
    toolVersion = libs.versions.pmd.get()
    ruleSetFiles = files("config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

spotbugs {
    toolVersion.set(libs.versions.spotbugsLibrary)
    excludeFilter = file("config/spotbugs/exclude-filter.xml")
    ignoreFailures = false
}

licenseReport {
    allowedLicensesFile = file("config/dependency-license-report/allowed-licenses.json")
    renderers =
        arrayOf<ReportRenderer>(XmlReportRenderer(), InventoryMarkdownReportRenderer(), InventoryHtmlReportRenderer())
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
        withType<MavenPublication>().configureEach {
            artifact(tasks.cyclonedxDirectBom) {
                classifier = "sbom"
                extension = "json"
            }
        }
    }
}

versionCatalogUpdate {
    keep {
        keepUnusedVersions = true
    }
}

tasks {
    val logIsCi by registering {
        onlyIf {
            isCi.get()
        }
        doLast {
            logger.lifecycle("Environment variable CI is set")
        }
    }
    withType<JavaCompile>().configureEach {
        options.encoding = StandardCharsets.UTF_8.name()
    }
    withType<ProcessResources>().configureEach {
        filteringCharset = StandardCharsets.UTF_8.name()
    }
    withType<Test>().configureEach {
        useJUnitPlatform()
        failFast = isCi.getOrElse(false)
        reports {
            html.required.set(isCi.map { !it })
            junitXml.required.set(isCi)
        }
    }
    jacocoTestReport {
        dependsOn(test)
        reports {
            html.required.set(isCi.map { !it })
            xml.required.set(isCi)
        }
    }
    withType<Checkstyle>().configureEach {
        exclude("**/module-info.java")
        reports {
            html.required.set(isCi.map { !it })
            xml.required.set(isCi)
        }
    }
    withType<Pmd>().configureEach {
        reports {
            html.required.set(isCi.map { !it })
            xml.required.set(isCi)
        }
    }
    withType<SpotBugsTask>().configureEach {
        val taskName = name
        reports {
            create("html") {
                required.set(isCi.map { !it })
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
        gradleVersion = project.providers.gradleProperty("gradleVersion").get()
        distributionType = DistributionType.ALL
    }
    build {
        dependsOn(logIsCi)
    }
    check {
        dependsOn(jacocoTestReport, buildHealth, checkLicense)
    }
}
