package com.longer.groupapp.presentation.login

import com.longer.groupapp.common.mvi.ViewStateChange

interface LogInViewStateChanges : ViewStateChange<LogInViewState> {
    class Initial : LogInViewStateChanges {
        override fun computeNewState(previousState: LogInViewState): LogInViewState {
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

    class Loading : LogInViewStateChanges {
        override fun computeNewState(previousState: LogInViewState): LogInViewState {
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

    class Error(private val errorRes: Int) : LogInViewStateChanges {
        override fun computeNewState(previousState: LogInViewState): LogInViewState {
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

    class LockUI(private val locked: Boolean) : LogInViewStateChanges {
        override fun computeNewState(previousState: LogInViewState): LogInViewState {
            return previousState.copy(
                    uiLocked = locked
            )
        }

        override fun toString(): String {
            return "LockUI"
        }
    }

    class Forward : LogInViewStateChanges {
        override fun computeNewState(previousState: LogInViewState): LogInViewState {
            return previousState.copy(
                    loading = false,
                    errorRes = null,
                    forward = true
            )
        }

        override fun toString(): String {
            return "Forward"
        }
    }
}