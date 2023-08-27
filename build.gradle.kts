plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "com.crowdproj.product.model"
version = "0.0.1"

repositories {
    mavenCentral()
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}

tasks {
    val deploy: Task by creating {
        dependsOn("build")
    }
}
