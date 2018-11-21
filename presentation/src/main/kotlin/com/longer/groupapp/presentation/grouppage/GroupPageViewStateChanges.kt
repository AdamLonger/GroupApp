package com.longer.groupapp.presentation.grouppage

import com.longer.groupapp.common.mvi.ViewStateChange
import model.GroupObject

interface GroupPageViewStateChanges : ViewStateChange<GroupPageViewState> {
    class Initial : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    loading = false,
                    data = null,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class SubPathInited(private val hasChild: Boolean) : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    hasChild = hasChild,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class SeenUpdated : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState) = previousState
        override fun toString() = "SeenUpdated"
    }

    class Subscribed : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    isSubscribed = true
            )
        }

        override fun toString(): String {
            return "Subscribed"
        }
    }

    class Unsubscribed : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    isSubscribed = false
            )
        }

        override fun toString(): String {
            return "Unsubscribed"
        }
    }

    class DataLoaded(private val group: GroupObject) : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    loading = false,
                    data = group,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Loading : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    loading = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class StopLoading : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    loading = false
            )
        }

        override fun toString(): String {
            return "StopLoading"
        }
    }

    class LockUI(private val locked: Boolean) : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    uiLocked = locked
            )
        }

        override fun toString(): String {
            return "LockUI"
        }
    }

    class Error(private val errorRes: Int) : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
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
