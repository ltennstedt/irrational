plugins {
    id("base-conventions")
    id("common-conventions")
    groovy
    codenarc
}

dependencies {
    api(project(":core"))
    implementation(libs.groovy)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.spock)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    val groovydocJar =
        register<Jar>("groovydocJar") {
            description = "Generating Groovydoc JAR"
            group = "build"
            from(named<Groovydoc>("groovydoc"))
            archiveClassifier = "javadoc"
        }
    named<CodeNarc>("codenarcMain") {
        configFile = file("../config/codenarc/codenarc-main.groovy")
    }
    named<CodeNarc>("codenarcTest") {
        configFile = file("../config/codenarc/codenarc-test.groovy")
    }
    generateMetadataFileForMavenPublication {
        dependsOn(groovydocJar)
    }
    assemble {
        dependsOn(groovydocJar)
    }
}

spotless {
    groovy {
        excludeJava()
        removeSemicolons()
        endWithNewline()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
    }
}

codenarc {
    toolVersion = "4.0.0"
    reportFormat =
        if (providers
                .environmentVariable("CI")
                .map {
                    it.equals("true", ignoreCase = true)
                }.orElse(false)
                .get()
        ) {
            "console"
        } else {
            "html"
        }
}

publishing {
    publications {
        named<MavenPublication>("maven") {
            artifact(tasks.named<Jar>("groovydocJar"))
        }
    }
}
