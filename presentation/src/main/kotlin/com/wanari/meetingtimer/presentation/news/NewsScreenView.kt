package com.wanari.meetingtimer.presentation.news

import com.wanari.meetingtimer.common.mvi.MviView
import io.reactivex.subjects.PublishSubject

interface NewsScreenView : MviView<NewsViewState>{
    fun setSubPath(): PublishSubject<String>
}
