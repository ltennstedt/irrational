import com.github.spotbugs.snom.SpotBugsTask

plugins {
    id("common-conventions")
    checkstyle
    pmd
    alias(libs.plugins.spotbugs)
}

dependencies {
    api(libs.jspecify)
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    val isCi: Provider<Boolean> =
        providers
            .environmentVariable("CI")
            .map { it.equals("true", ignoreCase = true) }
            .orElse(false)
    val isNotCi = isCi.map { !it }
    withType<Checkstyle>().configureEach {
        exclude("**/module-info.java")
        reports {
            html.required = isNotCi
            xml.required = isCi
        }
    }
    withType<Pmd>().configureEach {
        reports {
            html.required = isNotCi
            xml.required = isCi
        }
    }
    withType<SpotBugsTask>().configureEach {
        val taskName = name
        reports {
            create("html") {
                required = isNotCi
                outputLocation =
                    layout.buildDirectory.file("reports/spotbugs/$taskName.html")
            }
            create("xml") {
                required = isCi
                outputLocation =
                    layout.buildDirectory.file("reports/spotbugs/$taskName.xml")
            }
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withJavadocJar()
    withSourcesJar()
}

checkstyle {
    toolVersion = "12.3.1"
    configFile = file("${rootProject.projectDir.absolutePath}/config/checkstyle/checkstyle.xml")
    isIgnoreFailures = false
}

pmd {
    toolVersion = "7.26.0"
    ruleSetFiles = files("${rootProject.projectDir.absolutePath}/config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

spotbugs {
    toolVersion = "4.10.3"
    excludeFilter = file("${rootProject.projectDir.absolutePath}/config/spotbugs/exclude-filter.xml")
    ignoreFailures = false
}
