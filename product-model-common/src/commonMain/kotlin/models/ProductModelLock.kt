package models

import kotlin.jvm.JvmInline

@JvmInline
value class ProductModelLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ProductModelLock("")
    }
}