package com.longer.groupapp.common.mvi

interface ViewStateChange<VS> {
    fun computeNewState(previousState: VS): VS
}
