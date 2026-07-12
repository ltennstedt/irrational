plugins {
    `kotlin-dsl`
    alias(libs.plugins.ben.manes.versions)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.spotless.plugin)
    implementation(libs.licensee.plugin)
    implementation(libs.validate.poms.plugin)
    implementation(libs.cyclonedx.plugin)
    implementation(libs.ben.manes.versions.plugin)
}

configurations.configureEach {
    resolutionStrategy.componentSelection.all {
        if (candidate.version.endsWith("-SNAPSHOT", ignoreCase = true)) {
            reject("SNAPSHOT version rejected for ${candidate.group}:${candidate.module}:${candidate.version}")
        }
    }
}

dependencyLocking {
    lockAllConfigurations()
}
