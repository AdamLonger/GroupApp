package com.longer.groupapp.presentation.news.paging

import com.longer.groupapp.presentation.utils.PagingProvider
import interactor.NewsInteractor
import model.NewsObject

class NewsDataProvider(
        newsInteractor: NewsInteractor
) : PagingProvider<NewsObject>(newsInteractor)
