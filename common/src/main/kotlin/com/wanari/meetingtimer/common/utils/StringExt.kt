package com.wanari.meetingtimer.common.utils

fun String.nullIfEmpty(): String? {
    if (this.isEmpty()) {
        return null
    }
    return this
}
