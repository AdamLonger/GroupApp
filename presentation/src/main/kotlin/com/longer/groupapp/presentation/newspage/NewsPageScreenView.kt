package com.longer.groupapp.presentation.newspage

import com.longer.groupapp.common.mvi.MviView
import io.reactivex.subjects.PublishSubject

interface NewsPageScreenView : MviView<NewsPageViewState> {
    fun loadContent(): PublishSubject<String>
}
