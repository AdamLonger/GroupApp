package com.wanari.meetingtimer.presentation.news.paging

import android.arch.paging.ItemKeyedDataSource
import interactor.NewsInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import model.NewsObject

class NewsDataSource(private val newsInteractor: NewsInteractor) : ItemKeyedDataSource<String, NewsObject>() {

    private val disposables = CompositeDisposable()

    init {
        disposables.add(
                newsInteractor.getItemChangeSubject()
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.computation())
                        .subscribe {
                            invalidate()
                            disposables.clear()
                        }
        )
    }

    override fun loadInitial(params: ItemKeyedDataSource.LoadInitialParams<String>, callback: ItemKeyedDataSource.LoadInitialCallback<NewsObject>) {
        disposables.add(
                newsInteractor.getItems(params.requestedLoadSize).subscribe({
                    callback.onResult(it)
                }, {})
        )
    }

    override fun loadAfter(params: ItemKeyedDataSource.LoadParams<String>, callback: ItemKeyedDataSource.LoadCallback<NewsObject>) {
        disposables.add(
                newsInteractor.getItemsAfter(params.key, params.requestedLoadSize).subscribe({
                    callback.onResult(it)
                }, {})
        )
    }

    override fun loadBefore(params: ItemKeyedDataSource.LoadParams<String>, callback: ItemKeyedDataSource.LoadCallback<NewsObject>) {
        disposables.add(
                newsInteractor.getItemsBefore(params.key, params.requestedLoadSize).subscribe({
                    callback.onResult(it)
                }, {})
        )
    }

    override fun getKey(item: NewsObject): String {
        return item.objectKey ?: ""
    }
}
