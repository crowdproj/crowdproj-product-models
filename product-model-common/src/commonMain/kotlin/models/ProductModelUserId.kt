package models

import kotlin.jvm.JvmInline

@JvmInline
value class ProductModelUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ProductModelUserId("")
    }
}