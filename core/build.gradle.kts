import com.github.spotbugs.snom.SpotBugsTask

plugins {
    id("common-conventions")
    checkstyle
    pmd
    alias(libs.plugins.spotbugs)
}

group = "io.github.irrational"
version = "0.1.0-SNAPSHOT"

dependencies {
    api(libs.jspecify)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.platform)
}

checkstyle {
    toolVersion = "12.3.1"
    this.configFile = file("${rootProject.projectDir.absolutePath}/config/checkstyle/checkstyle.xml")
    isIgnoreFailures = false
}

pmd {
    toolVersion = "7.26.0"
    this.ruleSetFiles = files("${rootProject.projectDir.absolutePath}/config/pmd/ruleset.xml")
    isIgnoreFailures = false
}

spotbugs {
    toolVersion = "4.10.2"
    excludeFilter = file("${rootProject.projectDir.absolutePath}/config/spotbugs/exclude-filter.xml")
    ignoreFailures = false
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
}
