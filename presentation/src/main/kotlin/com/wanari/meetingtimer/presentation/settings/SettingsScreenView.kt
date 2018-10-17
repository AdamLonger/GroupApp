package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.common.mvi.MviView
import io.reactivex.Observable

interface SettingsScreenView : MviView<SettingsViewState> {
    fun logOut(): Observable<Any>
}
