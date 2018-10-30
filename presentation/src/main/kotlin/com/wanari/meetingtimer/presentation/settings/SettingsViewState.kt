package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.presentation.model.ProfileObject

data class SettingsViewState(
        val loading: Boolean = false,
        val errorRes: Int? = null,
        val forward: Boolean = false,
        val uiLocked: Boolean = false,
        val profile: ProfileObject? = null
)