import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("common-conventions")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotest)
    alias(libs.plugins.dokka)
    alias(libs.plugins.detekt)
}

dependencies {
    api(project(":core"))
    testImplementation(platform(libs.kotest.bom))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core-jvm")
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            apiVersion = KotlinVersion.KOTLIN_2_4
            languageVersion = KotlinVersion.KOTLIN_2_4
            progressiveMode = true
            extraWarnings = true
        }
    }
    val dokkaHtmlJar =
        register<Jar>("dokkaHtmlJar") {
            description = "Generating Dokka HTML documentation JAR"
            group = "dokka"
            from(dokkaGeneratePublicationHtml.flatMap { it.outputDirectory })
            archiveClassifier = "javadoc"
        }
    kotlinSourcesJar {
        enabled = false
    }
    withType<Detekt>().configureEach {
        reports {
            html {
                required =
                    providers
                        .environmentVariable("CI")
                        .map { it.equals("true", ignoreCase = true) }
                        .orElse(false)
                        .map { !it }
            }
            md {
                required = false
            }
            sarif {
                required = false
            }
            txt {
                required = false
            }
            xml {
                required = false
            }
        }
    }
    generateMetadataFileForMavenPublication {
        dependsOn(dokkaHtmlJar)
    }
    assemble {
        dependsOn(dokkaHtmlJar)
    }
}

spotless {
    kotlin {
        ktlint("1.8.0")
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

kotlin {
    explicitApi()
}

detekt {
    toolVersion = "1.23.8"
    config.from(files("../config/detekt/detekt.yaml"))
    buildUponDefaultConfig = true
}

publishing {
    publications {
        named<MavenPublication>("maven") {
            artifact(tasks.named<Jar>("dokkaHtmlJar"))
        }
    }
}
