import org.cyclonedx.gradle.CyclonedxDirectTask
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import java.nio.charset.StandardCharsets

plugins {
    id("base-conventions")
    `java-platform`
    id("org.cyclonedx.bom")
    `maven-publish`
    id("io.freefair.maven-central.validate-poms")
}

group = "io.github.irrational"
version = "0.1.0-SNAPSHOT"

dependencies {
    constraints {
        api(project(":core"))
        api(project(":jakarta-validation"))
        api(project(":kotlin"))
        api(project(":groovy"))
    }
}

tasks {
    withType<ProcessResources>().configureEach {
        filteringCharset = StandardCharsets.UTF_8.name()
    }
    withType<Jar>().configureEach {
        archiveBaseName = "${rootProject.name}-${project.name}"
    }
    withType<CyclonedxDirectTask>().configureEach {
        includeConfigs =
            listOf(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME, JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME)
        xmlOutput.unsetConvention()
    }
    named("localBuild") {
        dependsOn(named<PublishToMavenLocal>("publishMavenPublicationToMavenLocal"))
        enabled =
            providers
                .environmentVariable("CI")
                .map { it.equals("true", ignoreCase = true) }
                .orElse(false)
                .map { !it }
                .get()
    }
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
            from(components["javaPlatform"])
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
