package com.wanari.meetingtimer.presentation.news.paging

import com.wanari.meetingtimer.presentation.utils.PagingProvider
import interactor.NewsInteractor
import model.NewsObject

class NewsDataProvider(
        newsInteractor: NewsInteractor
) : PagingProvider<NewsObject>(newsInteractor)
