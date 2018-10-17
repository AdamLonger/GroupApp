package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.common.mvi.ViewStateChange

interface SettingsViewStateChanges : ViewStateChange<SettingsViewState> {
    class Initial : SettingsViewStateChanges {
        override fun computeNewState(previousState: SettingsViewState): SettingsViewState {
            return previousState.copy(
                    loading = false,
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
                    errorRes = errorRes
            )
        }

        override fun toString(): String {
            return "Error"
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