package com.wanari.meetingtimer.presentation.news

import com.wanari.meetingtimer.presentation.news.paging.NewsDataSource

data class NewsViewState(
        val loading: Boolean = false,
        val dataSource: NewsDataSource? = null,
        val errorRes: Int? = null
)