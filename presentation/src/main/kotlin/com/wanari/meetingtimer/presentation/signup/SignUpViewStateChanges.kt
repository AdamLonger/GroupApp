package com.wanari.meetingtimer.presentation.signup

import com.wanari.meetingtimer.common.mvi.ViewStateChange

interface SignUpViewStateChanges : ViewStateChange<SignUpViewState> {
    class Initial : SignUpViewStateChanges {
        override fun computeNewState(previousState: SignUpViewState): SignUpViewState {
            return previousState.copy(
                    loading = false,
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
                    errorRes = errorRes
            )
        }

        override fun toString(): String {
            return "Error"
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