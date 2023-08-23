plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val datetimeVersion = "0.4.0"
    implementation(kotlin("stdlib-common"))
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.0")
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}