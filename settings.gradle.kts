plugins {
    id("com.autonomousapps.build-health") version "3.5.1"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val gradleVersion = "8.14.4"
if (GradleVersion.current() < GradleVersion.version(gradleVersion)) {
    throw GradleException("Gradle $gradleVersion or newer required but was ${GradleVersion.current()}")
}

rootProject.name = "irrational"
