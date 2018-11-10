package com.longer.groupapp.presentation.grouppage.paging

import com.longer.groupapp.presentation.utils.PagingProvider
import interactor.NewsInteractor
import model.NewsObject

class GroupNewsDataProvider(
        newsInteractor: NewsInteractor
) : PagingProvider<NewsObject>(newsInteractor)