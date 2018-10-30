package com.wanari.meetingtimer.common.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAccessor

const val FIREBASE_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
const val FIREBASE_FORMAT_DATE = "yyyy-MM-dd"
const val FIREBASE_FORMAT_TIME = "HH:mm:ss"

const val DISPLAY_FORMAT_DATE_TIME = "yyyy.MM.dd HH:mm"
const val DISPLAY_FORMAT_DATE = "yyyy.MM.dd"
const val DISPLAY_FORMAT_TIME = "HH:mm"

fun LocalDate.toFirebaseString(): String? = toFirebaseString(FIREBASE_FORMAT_DATE)

fun LocalTime.toFirebaseString(): String? = toFirebaseString(FIREBASE_FORMAT_TIME)

fun LocalDateTime.toFirebaseString(): String? = toFirebaseString(FIREBASE_FORMAT_DATE_TIME)


fun String.toLocalDate(): LocalDate? = toThreeTenDate(FIREBASE_FORMAT_DATE)

fun String.toLocalTime(): LocalTime? = toThreeTenTime(FIREBASE_FORMAT_TIME)

fun String.toLocalDateTime(): LocalDateTime? = toThreeTenDateTime(FIREBASE_FORMAT_DATE_TIME)


fun LocalDate.toDisplayString(): String? = toFirebaseString(DISPLAY_FORMAT_DATE)

fun LocalTime.toDisplayString(): String? = toFirebaseString(DISPLAY_FORMAT_TIME)

fun LocalDateTime.toDisplayString(): String? = toFirebaseString(DISPLAY_FORMAT_DATE_TIME)


fun String.toDisplayLocalDate(): LocalDate? = toThreeTenDate(DISPLAY_FORMAT_DATE)

fun String.toDisplayLocalTime(): LocalTime? = toThreeTenTime(DISPLAY_FORMAT_TIME)

fun String.toDisplayLocalDateTime(): LocalDateTime? = toThreeTenDateTime(DISPLAY_FORMAT_DATE_TIME)


private fun TemporalAccessor.toFirebaseString(format: String): String? {
    return DateTimeFormatter.ofPattern(format).format(this)
}

private fun String.toThreeTenDate(format: String): LocalDate? {
    return LocalDate.from(DateTimeFormatter.ofPattern(format).parse(this))
}

private fun String.toThreeTenDateTime(format: String): LocalDateTime? {
    return LocalDateTime.from(DateTimeFormatter.ofPattern(format).parse(this))
}

private fun String.toThreeTenTime(format: String): LocalTime? {
    return LocalTime.from(DateTimeFormatter.ofPattern(format).parse(this))
}
