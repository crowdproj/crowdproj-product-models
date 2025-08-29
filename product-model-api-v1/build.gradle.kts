import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.crowdproj.generator)
    alias(libs.plugins.kotlinx.serialization)
}

val apiVersion = "v1"
val apiSpec: Configuration by configurations.creating

dependencies {
    apiSpec(
        group = "com.crowdproj",
        name = "specs-v1",
        version = libs.versions.api.spec.get(),
        classifier = "openapi",
        ext = "yaml"
    )
}

kotlin {
    jvm { }
    linuxX64 { }

    sourceSets {
        val serializationVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {

            kotlin.srcDirs(layout.buildDirectory.dir("generate-resources/src/commonMain/kotlin"))
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

crowdprojGenerate {
    packageName.set("${project.group}.api.v1")
    inputSpec.set("${project.buildDir}/spec-product-model-$apiVersion.yaml")
}

val getSpecs: Task by tasks.creating {
    doFirst {
        copy {
            from("${rootProject.projectDir}/specs")
            into(project.buildDir.toString())
        }
        copy {
            from(apiSpec.asPath)
            into(project.buildDir.toString())
            rename { "base.yaml" }
        }
    }
}

tasks {
    this.openApiGenerate {
        dependsOn(getSpecs)
    }
}

tasks {
    val openApiGenerateTask: GenerateTask = getByName("openApiGenerate", GenerateTask::class) {
        outputDir.set(layout.buildDirectory.file("generate-resources").get().toString())
        finalizedBy("compileCommonMainKotlinMetadata")
    }
    filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerateTask)
    }
}