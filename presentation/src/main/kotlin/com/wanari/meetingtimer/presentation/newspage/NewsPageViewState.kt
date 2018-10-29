package com.wanari.meetingtimer.presentation.newspage

import model.NewsObject

data class NewsPageViewState(
        val loading: Boolean = false,
        val data: NewsObject? = null,
        val errorRes: Int? = null
)
