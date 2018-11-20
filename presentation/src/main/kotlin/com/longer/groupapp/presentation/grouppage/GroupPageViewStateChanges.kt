package com.longer.groupapp.presentation.grouppage

import com.longer.groupapp.common.mvi.ViewStateChange
import model.GroupObject

interface GroupPageViewStateChanges : ViewStateChange<GroupPageViewState> {
    class Initial : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    loading = false,
                    data = null,
                    subPathSet = false,
                    seenUpdated = false,
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
                    subPathSet = true,
                    seenUpdated = false,
                    hasChild = hasChild,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
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

    class SeenUpdated : GroupPageViewStateChanges {
        override fun computeNewState(previousState: GroupPageViewState): GroupPageViewState {
            return previousState.copy(
                    seenUpdated = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
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
