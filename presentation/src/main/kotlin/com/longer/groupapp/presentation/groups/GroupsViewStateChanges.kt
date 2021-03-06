package com.longer.groupapp.presentation.groups

import com.longer.groupapp.common.mvi.ViewStateChange

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

    class ChildCounted(private val hasChild:Boolean) : GroupsViewStateChanges {
        override fun computeNewState(previousState: GroupsViewState): GroupsViewState {
            return previousState.copy(
                    loading = false,
                    hasChild = hasChild,
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