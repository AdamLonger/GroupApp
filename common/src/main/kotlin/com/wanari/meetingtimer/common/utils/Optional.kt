package com.wanari.meetingtimer.common.utils

sealed class Optional<out T : Any> {
    @JvmSynthetic
    abstract operator fun component1(): T?
}

data class Some<out T : Any>(val value: T) : Optional<T>() {
    override fun toString() = "Some($value)"
}

object None : Optional<Nothing>() {
    override fun toString() = "None"

    override fun component1(): Nothing? = null
}

fun <T : Any> T?.toOptional(): Optional<T> = if (this == null) None else Some(this)
