package com.wanari.meetingtimer.presentation.login

import com.wanari.meetingtimer.common.mvi.MviView
import io.reactivex.Observable

interface LogInScreenView : MviView<LogInViewState> {
    fun logIn(): Observable<Pair<String, String>>
}
