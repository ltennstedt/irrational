plugins {
    alias(libs.plugins.spotless)
    `java-library`
    jacoco
    checkstyle
    pmd
    alias(libs.plugins.spotbugs)
    `maven-publish`
    alias(libs.plugins.version.catalog.update)
    `project-report`
}

group = "irrational"
version = "0.0.1-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    constraints {
        testImplementation(libs.byte.buddy) {
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
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)
    testImplementation(libs.equalsverifier)
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
    format("generic") {
        target("**/*.*")
        targetExclude(".gradle/**", ".idea/**", "build/**", "*.iml")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
    kotlinGradle {
        ktlint("1.8.0")
    }
    java {
        palantirJavaFormat("2.83.0").formatJavadoc(true)
        forbidModuleImports()
        forbidWildcardImports()
        formatAnnotations()
        removeUnusedImports()
    }
    yaml {
        target("**/*.yaml")
        jackson().yamlFeature("MINIMIZE_QUOTES", true)
    }
    flexmark {
        target("**/*.md")
        flexmark("0.64.6")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withJavadocJar()
    withSourcesJar()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit)
        }
    }
}

jacoco {
    toolVersion = "0.8.14"
}

checkstyle {
    toolVersion = "12.3.0"
}

pmd {
    toolVersion = "7.19.0"
    ruleSetFiles = files("config/pmd/ruleset.xml")
}

spotbugs {
    toolVersion = "4.9.8"
    excludeFilter = file("config/spotbugs/exclude-filter.xml")
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
                distributionManagement {
                    repository {
                        id = "github"
                        name = "GitHub Packages"
                        url = "https://maven.pkg.github.com/ltennstedt/irrational/"
                    }
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
    test {
        finalizedBy(jacocoTestReport)
    }
    jacocoTestReport {
        dependsOn(test)
        reports {
            xml.required = true
        }
    }
    register("lint") {
        description = "run linter and analysis tasks"
        group = "verification"
        dependsOn.addAll(
            listOf(
                buildHealth,
                spotlessCheck,
                checkstyleMain,
                checkstyleTest,
                pmdMain,
                pmdMain,
                spotbugsMain,
                spotbugsTest,
            ),
        )
    }
    wrapper {
        gradleVersion = "9.2.1"
    }
}
