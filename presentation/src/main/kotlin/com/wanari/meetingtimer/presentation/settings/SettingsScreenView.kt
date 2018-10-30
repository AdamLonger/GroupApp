package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.common.mvi.MviView
import com.wanari.meetingtimer.presentation.model.ProfileObject
import io.reactivex.Observable
import model.SettingsObject

interface SettingsScreenView : MviView<SettingsViewState> {
    fun logOut(): Observable<Any>
    fun saveSettings(): Observable<SettingsObject>
    fun saveProfile(): Observable<ProfileObject>
}
