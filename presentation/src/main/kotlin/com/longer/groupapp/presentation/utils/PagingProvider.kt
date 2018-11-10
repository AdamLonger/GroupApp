package com.longer.groupapp.presentation.utils

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import interactor.PagingSourceInteractor
import model.FirebaseObject

private const val PAGE_SIZE = 4

abstract class PagingProvider<T : FirebaseObject>(
        private val interactor: PagingSourceInteractor<T>,
        private val pageSize: Int = PAGE_SIZE) {

    private var itemDataFactory = PagingDataFactory(interactor)

    open fun getItems(): LiveData<PagedList<T>>? {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        return LivePagedListBuilder(itemDataFactory, config)
                .setInitialLoadKey("")
                .build()
    }
}