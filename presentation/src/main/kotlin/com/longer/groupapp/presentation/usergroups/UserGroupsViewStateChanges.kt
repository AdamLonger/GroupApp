package com.longer.groupapp.presentation.usergroups

import com.longer.groupapp.common.mvi.ViewStateChange

interface UserGroupsViewStateChanges : ViewStateChange<UserGroupsViewState> {
    class Initial : UserGroupsViewStateChanges {
        override fun computeNewState(previousState: UserGroupsViewState): UserGroupsViewState {
            return previousState.copy(
                    loading = false,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class Loading : UserGroupsViewStateChanges {
        override fun computeNewState(previousState: UserGroupsViewState): UserGroupsViewState {
            return previousState.copy(
                    loading = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Error(private val errorRes: Int) : UserGroupsViewStateChanges {
        override fun computeNewState(previousState: UserGroupsViewState): UserGroupsViewState {
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