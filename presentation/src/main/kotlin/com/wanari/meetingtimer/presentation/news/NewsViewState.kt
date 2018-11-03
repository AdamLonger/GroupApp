package com.wanari.meetingtimer.presentation.news

data class NewsViewState(
        val loading: Boolean = false,
        val subPathSet: Boolean = false,
        val errorRes: Int? = null
)