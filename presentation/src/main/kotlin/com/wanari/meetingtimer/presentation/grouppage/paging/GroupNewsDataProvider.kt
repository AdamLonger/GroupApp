package com.wanari.meetingtimer.presentation.grouppage.paging

import com.wanari.meetingtimer.presentation.utils.PagingProvider
import interactor.NewsInteractor
import model.NewsObject

class GroupNewsDataProvider(
        newsInteractor: NewsInteractor
) : PagingProvider<NewsObject>(newsInteractor)