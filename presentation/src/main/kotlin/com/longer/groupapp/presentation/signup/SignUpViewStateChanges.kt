package com.longer.groupapp.presentation.signup

import com.longer.groupapp.common.mvi.ViewStateChange

interface SignUpViewStateChanges : ViewStateChange<SignUpViewState> {
    class Initial : SignUpViewStateChanges {
        override fun computeNewState(previousState: SignUpViewState): SignUpViewState {
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

    class Loading : SignUpViewStateChanges {
        override fun computeNewState(previousState: SignUpViewState): SignUpViewState {
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

    class Error(private val errorRes: Int) : SignUpViewStateChanges {
        override fun computeNewState(previousState: SignUpViewState): SignUpViewState {
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

    class LockUI(private val locked:Boolean) : SignUpViewStateChanges {
        override fun computeNewState(previousState: SignUpViewState): SignUpViewState {
            return previousState.copy(
                    uiLocked = locked
            )
        }

        override fun toString(): String {
            return "LockUI"
        }
    }

    class Forward : SignUpViewStateChanges {
        override fun computeNewState(previousState: SignUpViewState): SignUpViewState {
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