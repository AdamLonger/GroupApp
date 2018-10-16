package com.wanari.meetingtimer.common.utils

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
