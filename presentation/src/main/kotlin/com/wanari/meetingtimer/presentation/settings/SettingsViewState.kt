package com.wanari.meetingtimer.presentation.settings

data class SettingsViewState(
        val loading: Boolean = false,
        val errorRes: Int? = null,
        val forward:Boolean = false
)