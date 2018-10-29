package com.wanari.meetingtimer.presentation.news

import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.presentation.news.paging.NewsDataSource

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