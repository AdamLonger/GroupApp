package com.longer.groupapp.presentation.news

data class NewsViewState(
        val loading: Boolean = false,
        val subPathSet: Boolean = false,
        val hasChild: Boolean = true,
        val errorRes: Int? = null
)