package com.wanari.meetingtimer.presentation.newspage

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import interactor.NewsPageInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class NewsPagePresenter(initialState: NewsPageViewState, private val interactor: NewsPageInteractor) : BasePresenter<NewsPageScreenView, NewsPageViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<NewsPageViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<NewsPageViewState>>>()

        result.add(intent { view -> view.loadContent() }
                .flatMap { key ->
                    interactor.getNewsContent(key)
                            .mapViewStateChange { NewsPageViewStateChanges.DataLoaded(it) }
                            .onErrorReturn { error -> NewsPageViewStateChanges.Error(0) }
                            .startWith(NewsPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        return result
    }

}
