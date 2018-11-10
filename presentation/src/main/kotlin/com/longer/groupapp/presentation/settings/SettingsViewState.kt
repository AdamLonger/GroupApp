package com.longer.groupapp.presentation.settings

import model.ProfileObject

data class SettingsViewState(
        val loading: Boolean = false,
        val errorRes: Int? = null,
        val forward: Boolean = false,
        val uiLocked: Boolean = false,
        val profile: ProfileObject? = null
)