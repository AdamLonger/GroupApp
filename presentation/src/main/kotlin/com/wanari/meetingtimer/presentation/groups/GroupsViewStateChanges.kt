package com.wanari.meetingtimer.presentation.groups

import com.wanari.meetingtimer.common.mvi.ViewStateChange

interface GroupsViewStateChanges : ViewStateChange<GroupsViewState> {
    class Initial : GroupsViewStateChanges {
        override fun computeNewState(previousState: GroupsViewState): GroupsViewState {
            return previousState.copy(
                    loading = false,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class Loading : GroupsViewStateChanges {
        override fun computeNewState(previousState: GroupsViewState): GroupsViewState {
            return previousState.copy(
                    loading = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Error(private val errorRes: Int) : GroupsViewStateChanges {
        override fun computeNewState(previousState: GroupsViewState): GroupsViewState {
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