package com.wanari.meetingtimer.presentation.grouppage.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanari.meetingtimer.presentation.news.paging.NewsDataFactory
import interactor.NewsInteractor
import model.NewsObject

private const val PAGE_SIZE = 4

class GroupNewsDataProvider(newsInteractor: NewsInteractor) {
    private var itemDataFactory = NewsDataFactory(newsInteractor)

    fun getItems(): LiveData<PagedList<NewsObject>>? {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        return LivePagedListBuilder(itemDataFactory, config)
                .setInitialLoadKey("")
                .build()
    }
}
