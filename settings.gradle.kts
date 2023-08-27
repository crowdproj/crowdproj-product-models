rootProject.name = "crowdproj-product-models"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val codeGeneratorVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("com.crowdproj.generator") version codeGeneratorVersion apply false
    }
}

include("product-model-api-v1")
include("product-model-common")
include("product-model-mappers")
