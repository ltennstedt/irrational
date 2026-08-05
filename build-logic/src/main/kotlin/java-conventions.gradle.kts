import com.github.spotbugs.snom.SpotBugsTask

plugins {
    id("com.diffplug.spotless")
    `java-library`
    checkstyle
    pmd
    id("com.github.spotbugs")
}

dependencies {
    api("org.jspecify:jspecify:1.0.1")
    testImplementation(platform("org.junit:junit-bom:6.1.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit-pioneer:junit-pioneer:2.3.0")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
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

spotless {
    java {
        palantirJavaFormat("2.96.0").formatJavadoc(true)
        forbidModuleImports()
        forbidWildcardImports()
        formatAnnotations()
        removeUnusedImports()
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

java {
    withJavadocJar()
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
