package com.longer.groupapp.presentation.settings

import com.longer.groupapp.common.mvi.ViewStateChange
import model.ProfileObject

interface SettingsViewStateChanges : ViewStateChange<SettingsViewState> {
    class Initial : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    loading = false,
                    uiLocked = false,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class Loading : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    loading = true,
                    uiLocked = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Error(private val errorRes: Int) : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    loading = false,
                    uiLocked = false,
                    errorRes = errorRes
            )
        }

        override fun toString(): String {
            return "Error"
        }
    }

    class ProfileLoaded(private val profileObject: ProfileObject) : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    profile = profileObject,
                    uiLocked = false,
                    loading = false,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "ProfileLoaded"
        }
    }

    class LockUI(private val locked: Boolean) : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    uiLocked = locked
            )
        }

        override fun toString(): String {
            return "LockUI"
        }
    }

    class Forward : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    loading = false,
                    forward = true
            )
        }

        override fun toString(): String {
            return "Error"
        }
    }
}