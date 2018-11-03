package com.wanari.meetingtimer.common.ui

import io.reactivex.processors.BehaviorProcessor

object AppStateManager {
    private val foregroundSubject = BehaviorProcessor.createDefault<Boolean>(false)
    private val networkSubject = BehaviorProcessor.createDefault<Boolean>(false)
    private val loadingSubject = BehaviorProcessor.createDefault<Boolean>(false)

    fun setLoadingState(state: Boolean) = loadingSubject.onNext(state)
    fun setForegroundState(state: Boolean) = foregroundSubject.onNext(state)
    fun getForegroundState() = foregroundSubject

    fun setNetworkState(state: Boolean) = networkSubject.onNext(state)
    fun getNetworkState() = networkSubject.distinctUntilChanged()
    fun getLoadingState() = loadingSubject.distinctUntilChanged()
}
