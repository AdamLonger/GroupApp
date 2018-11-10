package com.longer.groupapp.presentation.settings

import com.longer.groupapp.common.mvi.MviView
import model.ProfileObject
import io.reactivex.Observable
import model.SettingsObject

interface SettingsScreenView : MviView<SettingsViewState> {
    fun logOut(): Observable<Any>
    fun saveSettings(): Observable<SettingsObject>
    fun saveProfile(): Observable<ProfileObject>
}
