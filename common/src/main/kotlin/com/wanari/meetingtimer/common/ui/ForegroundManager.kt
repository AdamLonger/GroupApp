package com.wanari.meetingtimer.common.ui

import io.reactivex.processors.BehaviorProcessor

object ForegroundManager {
    private val stateSubject = BehaviorProcessor.createDefault<Boolean>(false)

    fun setState(state: Boolean) = stateSubject.onNext(state)
    fun getState() = stateSubject
}
