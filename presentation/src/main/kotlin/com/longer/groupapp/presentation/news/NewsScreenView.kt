package com.longer.groupapp.presentation.news

import com.longer.groupapp.common.mvi.MviView
import io.reactivex.subjects.PublishSubject

interface NewsScreenView : MviView<NewsViewState>{
    fun setSubPath(): PublishSubject<String>
}
