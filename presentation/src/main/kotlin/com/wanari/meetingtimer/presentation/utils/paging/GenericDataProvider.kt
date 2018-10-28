package com.wanari.meetingtimer.presentation.utils.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import model.FirebaseObject

private const val PAGE_SIZE = 4

class GenericDataProvider<T: FirebaseObject>(DS: ItemKeyedDataSource<String, T>) {
 
    var itemDataFactory: GenericDataFactory<T> = GenericDataFactory(DS)
 
    fun getItems(): LiveData<PagedList<T>>? {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()
 
        return LivePagedListBuilder(itemDataFactory, config)
                .setInitialLoadKey("")
                .build()
    }
}