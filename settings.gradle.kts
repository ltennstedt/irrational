plugins {
    id("com.autonomousapps.build-health") version "3.5.1"
}

val gradleVersion = "9.2"
if (GradleVersion.current() < GradleVersion.version(gradleVersion)) {
    throw GradleException("Gradle $gradleVersion or newer required")
}

rootProject.name = "irrational"
