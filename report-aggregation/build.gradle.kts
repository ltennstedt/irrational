plugins {
    id("base-conventions")
    `java-library`
    `jvm-test-suite`
    `jacoco-report-aggregation`
}

dependencies {
    jacocoAggregation(project(":core"))
    jacocoAggregation(project(":kotlin"))
    jacocoAggregation(project(":groovy"))
}

tasks {
    withType<JacocoReport>().configureEach {
        reports {
            html.required = isNotCi
            xml.required = isCi
        }
    }
    check {
        dependsOn(testCodeCoverageReport)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnitJupiter(libs.versions.junit.get())
        }
    }
}
