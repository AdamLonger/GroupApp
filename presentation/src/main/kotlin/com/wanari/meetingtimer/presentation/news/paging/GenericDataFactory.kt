package com.wanari.meetingtimer.presentation.news.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import interactor.NewsInteractor
import model.NewsObject

class NewsDataFactory(private val newsInteractor: NewsInteractor) : DataSource.Factory<String, NewsObject>() {

    private var datasourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): NewsDataSource? {
        val dataSource = NewsDataSource(newsInteractor)
        datasourceLiveData.postValue(dataSource)
        return dataSource
    }
}