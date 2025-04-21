plugins {
    id("com.autonomousapps.build-health") version "3.6.1"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val gradleVersion: String by settings
if (GradleVersion.current() < GradleVersion.version(gradleVersion)) {
    throw GradleException("Gradle $gradleVersion or newer required but was ${GradleVersion.current()}")
}

rootProject.name = "irrational"
