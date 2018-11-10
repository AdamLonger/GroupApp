package com.longer.groupapp.presentation.login

import com.longer.groupapp.common.mvi.MviView
import io.reactivex.Observable

interface LogInScreenView : MviView<LogInViewState> {
    fun logIn(): Observable<Pair<String, String>>
}
