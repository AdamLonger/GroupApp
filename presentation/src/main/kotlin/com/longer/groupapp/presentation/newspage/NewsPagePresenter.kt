package com.longer.groupapp.presentation.newspage

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import com.longer.groupapp.presentation.R
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
                            .onErrorReturn { _ -> NewsPageViewStateChanges.Error(R.string.message_error) }
                            .startWith(NewsPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        return result
    }

}
