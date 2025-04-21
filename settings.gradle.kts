plugins {
    id("com.autonomousapps.build-health") version "3.5.1"
    id("org.jetbrains.kotlin.jvm") version "2.3.0" apply false
}

val gradleVersion = "9.2"
if (GradleVersion.current() < GradleVersion.version(gradleVersion)) {
    throw GradleException("Gradle $gradleVersion or newer required but was ${GradleVersion.current()}")
}

rootProject.name = "irrational"
