package com.wanari.meetingtimer.presentation.newspage

import com.wanari.meetingtimer.common.mvi.MviView
import io.reactivex.subjects.PublishSubject
import model.FirebaseObject

interface NewsPageScreenView : MviView<NewsPageViewState> {
    fun loadContent(): PublishSubject<String>
}
