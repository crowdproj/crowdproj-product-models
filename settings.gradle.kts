rootProject.name = "crowdproj-product-models"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiVersion = "6.6.0"

        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("product-models-api-v1-jackson")
include("product-models-common")
include("product-models-mappers")
