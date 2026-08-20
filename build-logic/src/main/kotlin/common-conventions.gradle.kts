import org.cyclonedx.gradle.CyclonedxDirectTask
import java.nio.charset.StandardCharsets

plugins {
    `java-library`
    id("com.diffplug.spotless")
    jacoco
    id("io.freefair.maven-central.validate-poms")
    id("org.cyclonedx.bom")
    id("app.cash.licensee")
    `maven-publish`
}

tasks {
    withType<ProcessResources>().configureEach {
        filteringCharset = StandardCharsets.UTF_8.name()
    }
    withType<Test>().configureEach {
        useJUnitPlatform()
        failFast = isCi.get()
        reports {
            html.required = isNotCi
            junitXml.required = isCi
        }
    }
    withType<JacocoReport>().configureEach {
        dependsOn(test)
        reports {
            html.required = isNotCi
            xml.required = isCi
        }
    }
    withType<Jar>().configureEach {
        archiveBaseName = "${rootProject.name}-${project.name}"
    }
    withType<CyclonedxDirectTask>().configureEach {
        includeConfigs =
            listOf(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME, JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME)
        xmlOutput.unsetConvention()
    }
    check {
        dependsOn(jacocoTestReport, licensee)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
}

jacoco {
    toolVersion = "0.8.15"
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
            artifact(tasks.named<CyclonedxDirectTask>("cyclonedxDirectBom")) {
                classifier = "bom"
                extension = "json"
            }
            pom {
                artifactId = "${rootProject.name}-${project.name}"
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
