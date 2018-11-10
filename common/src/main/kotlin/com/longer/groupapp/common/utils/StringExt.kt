package com.longer.groupapp.common.utils

fun String.nullIfEmpty(): String? {
    if (this.isEmpty()) {
        return null
    }
    return this
}
