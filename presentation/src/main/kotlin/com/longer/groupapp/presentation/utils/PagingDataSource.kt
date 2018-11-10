package com.longer.groupapp.presentation.utils

import android.arch.paging.ItemKeyedDataSource
import interactor.PagingSourceInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import model.FirebaseObject

class PagingDataSource<T:FirebaseObject>(
        private val interactor: PagingSourceInteractor<T>
) : ItemKeyedDataSource<String, T>() {

    private val disposables = CompositeDisposable()

    init {
        disposables.add(
                interactor.getItemChangeSubject()
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.computation())
                        .subscribe {
                            invalidate()
                            disposables.clear()
                        }
        )
    }

    override fun loadInitial(params: ItemKeyedDataSource.LoadInitialParams<String>, callback: ItemKeyedDataSource.LoadInitialCallback<T>) {
        disposables.add(
                interactor.getItems(params.requestedLoadSize).subscribe({
                    callback.onResult(it)
                }, {})
        )
    }

    override fun loadAfter(params: ItemKeyedDataSource.LoadParams<String>, callback: ItemKeyedDataSource.LoadCallback<T>) {
        disposables.add(
                interactor.getItemsAfter(params.key, params.requestedLoadSize).subscribe({
                    callback.onResult(it)
                }, {})
        )
    }

    override fun loadBefore(params: ItemKeyedDataSource.LoadParams<String>, callback: ItemKeyedDataSource.LoadCallback<T>) {
        disposables.add(
                interactor.getItemsBefore(params.key, params.requestedLoadSize).subscribe({
                    callback.onResult(it)
                }, {})
        )
    }

    override fun getKey(item: T): String {
        return item.objectKey ?: ""
    }
}
