package models

data class ProductModelError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val title: String = "",
    val description: String = "",
    val exception: Throwable? = null,
    val level: Level = Level.ERROR,
) {
    @Suppress("unused")
    enum class Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}