package com.wanari.meetingtimer.presentation.signup

import com.wanari.meetingtimer.common.mvi.MviView
import io.reactivex.Observable

interface SignUpScreenView : MviView<SignUpViewState> {
    fun signUp(): Observable<Pair<String, String>>
}
