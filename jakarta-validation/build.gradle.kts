plugins {
    id("base-conventions")
    id("common-conventions")
    id("java-conventions")
}

dependencies {
    api(libs.jakarta.validation)
    api(project(":core"))
    testImplementation(libs.hibernate.validator)
    testRuntimeOnly(libs.expressly)
}
