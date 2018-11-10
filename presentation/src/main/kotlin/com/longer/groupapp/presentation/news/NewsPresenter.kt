package com.longer.groupapp.presentation.news

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import com.longer.groupapp.presentation.R
import interactor.NewsInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NewsPresenter(initialState: NewsViewState, private val newsInteractor: NewsInteractor) : BasePresenter<NewsScreenView, NewsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<NewsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<NewsViewState>>>()

        result.add(intent { view -> view.setSubPath() }
                .flatMap { path ->
                    newsInteractor.setSubPath(path)
                            .mapViewStateChange { NewsViewStateChanges.SubPathInited() }
                            .onErrorReturn { _ -> NewsViewStateChanges.Error(R.string.message_error) }
                            .startWith(NewsViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        return result
    }

}
