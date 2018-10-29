package com.wanari.meetingtimer.presentation.newspage

import com.wanari.meetingtimer.common.mvi.ViewStateChange
import model.NewsObject

interface NewsPageViewStateChanges : ViewStateChange<NewsPageViewState> {
    class Initial : NewsPageViewStateChanges {
        override fun computeNewState(previousState: NewsPageViewState): NewsPageViewState {
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

    class DataLoaded(private val news: NewsObject) : NewsPageViewStateChanges {
        override fun computeNewState(previousState: NewsPageViewState): NewsPageViewState {
            return previousState.copy(
                    loading = false,
                    data = news,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Loading : NewsPageViewStateChanges {
        override fun computeNewState(previousState: NewsPageViewState): NewsPageViewState {
            return previousState.copy(
                    loading = true,
                    errorRes = null
            )
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class Error(private val errorRes: Int) : NewsPageViewStateChanges {
        override fun computeNewState(previousState: NewsPageViewState): NewsPageViewState {
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
