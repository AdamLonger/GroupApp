package com.longer.groupapp.common.ui

import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.TimeUnit

const val STATE_DEBOUNCE_MILLISECONDS = 200L

object AppStateManager {
    private val foregroundSubject = BehaviorProcessor.createDefault<Boolean>(false)
    private val networkSubject = BehaviorProcessor.createDefault<Boolean>(false)
    private val loadingSubject = BehaviorProcessor.createDefault<Boolean>(false)

    fun setLoadingState(state: Boolean) = loadingSubject.onNext(state)
    fun setForegroundState(state: Boolean) = foregroundSubject.onNext(state)
    fun setNetworkState(state: Boolean) = networkSubject.onNext(state)

    fun getForegroundState() = foregroundSubject.distinctUntilChanged()
    fun getNetworkState() = networkSubject.distinctUntilChanged()
            .debounce(STATE_DEBOUNCE_MILLISECONDS, TimeUnit.MILLISECONDS)

    fun getLoadingState() = loadingSubject.distinctUntilChanged()
}
