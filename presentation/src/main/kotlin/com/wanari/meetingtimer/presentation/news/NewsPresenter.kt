package com.wanari.meetingtimer.presentation.news

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import interactor.NewsInteractor
import io.reactivex.Observable

class NewsPresenter(initialState: NewsViewState, private val newsInteractor: NewsInteractor) : BasePresenter<NewsScreenView, NewsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<NewsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<NewsViewState>>>()

        return result
    }

}
