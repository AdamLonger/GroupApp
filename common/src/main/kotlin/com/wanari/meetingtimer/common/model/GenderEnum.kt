package com.wanari.meetingtimer.common.model

import android.content.Context
import com.wanari.meetingtimer.common.R

const val MALE_TEXT = "Male"
const val FEMALE_TEXT = "Female"

enum class GenderEnum { Male, Female }

fun GenderEnum.getValue(context: Context): String? {
    return when (this) {
        GenderEnum.Male -> context.resources.getString(R.string.male_text)
        GenderEnum.Female -> context.resources.getString(R.string.female_text)
        else -> null
    }
}

fun GenderEnum.getStringRes(): Int? {
    return when (this) {
        GenderEnum.Male -> R.string.male_text
        GenderEnum.Female -> R.string.female_text
        else -> null
    }
}

fun GenderEnum.getName(): String {
    return when (this) {
        GenderEnum.Male -> MALE_TEXT
        GenderEnum.Female -> FEMALE_TEXT
    }
}