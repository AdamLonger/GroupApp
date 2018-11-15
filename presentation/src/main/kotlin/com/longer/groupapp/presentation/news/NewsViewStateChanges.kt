package com.longer.groupapp.presentation.news

import com.longer.groupapp.common.mvi.ViewStateChange

interface NewsViewStateChanges : ViewStateChange<NewsViewState> {
    class Initial : NewsViewStateChanges {
        override fun computeNewState(previousState: NewsViewState): NewsViewState {
            return previousState.copy(
                    loading = false,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class SubPathInited(private val hasChild: Boolean) : NewsViewStateChanges {
        override fun computeNewState(previousState: NewsViewState): NewsViewState {
            return previousState.copy(
                    loading = false,
                    subPathSet = true,
                    hasChild = hasChild,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Initial"
        }
    }

    class Loading : NewsViewStateChanges {
        override fun computeNewState(previousState: NewsViewState): NewsViewState {
            return previousState.copy(
                    loading = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Error(private val errorRes: Int) : NewsViewStateChanges {
        override fun computeNewState(previousState: NewsViewState): NewsViewState {
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