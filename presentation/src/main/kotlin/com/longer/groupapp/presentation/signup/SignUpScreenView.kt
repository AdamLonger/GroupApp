package com.longer.groupapp.presentation.signup

import com.longer.groupapp.common.mvi.MviView
import io.reactivex.Observable

interface SignUpScreenView : MviView<SignUpViewState> {
    fun signUp(): Observable<Pair<String, String>>
}
