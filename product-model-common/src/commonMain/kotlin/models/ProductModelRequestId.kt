package models

import kotlin.jvm.JvmInline

@JvmInline
value class ProductModelRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ProductModelRequestId("")
    }
}