package com.wanari.meetingtimer.common.utils

import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView

fun View.setVisiblity(boolean: Boolean) {
    visibility = when (boolean) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}

fun TextView.clear() {
    text = ""
}

fun EditText?.isEmptyOrNull(): Boolean {
    this?.text?.let {
        return it.toString().isEmpty()
    }
    return true
}

fun EditText?.lock(isLocked: Boolean, type: Int = InputType.TYPE_CLASS_TEXT) {
    this?.apply {
        isEnabled = !isLocked
        isFocusable = !isLocked
        isFocusableInTouchMode = !isLocked
        inputType = when (isLocked) {
            true -> InputType.TYPE_NULL
            false -> type
        }
    }
}
