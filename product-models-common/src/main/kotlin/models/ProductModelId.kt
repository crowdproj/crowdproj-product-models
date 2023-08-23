package models

@JvmInline
value class ProductModelId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ProductModelId("")
    }
}