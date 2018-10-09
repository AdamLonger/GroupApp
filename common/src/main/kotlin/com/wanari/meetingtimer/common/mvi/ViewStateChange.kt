package com.wanari.meetingtimer.common.mvi

interface ViewStateChange<VS> {
    fun computeNewState(previousState: VS): VS
}
