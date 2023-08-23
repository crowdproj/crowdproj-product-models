plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":product-models-api-v1-jackson"))
    implementation(project(":product-models-common"))

    testImplementation(kotlin("test-junit"))
}