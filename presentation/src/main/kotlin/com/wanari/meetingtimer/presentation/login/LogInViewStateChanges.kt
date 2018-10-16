package com.wanari.meetingtimer.presentation.login

import com.wanari.meetingtimer.common.mvi.ViewStateChange

interface LogInViewStateChanges : ViewStateChange<LogInViewState> {
    class Initial : LogInViewStateChanges {
        override fun computeNewState(previousState: LogInViewState): LogInViewState {
            return previousState.copy(
                    loading = false,
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
                    errorRes = errorRes
            )
        }

        override fun toString(): String {
            return "Error"
        }
    }
}