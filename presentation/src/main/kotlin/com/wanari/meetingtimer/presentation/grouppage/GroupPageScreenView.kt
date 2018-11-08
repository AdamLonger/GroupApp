package com.wanari.meetingtimer.presentation.grouppage

import com.wanari.meetingtimer.common.mvi.MviView
import io.reactivex.subjects.PublishSubject

interface GroupPageScreenView : MviView<GroupPageViewState> {
    fun loadContent(): PublishSubject<String>
    fun setSubPath(): PublishSubject<String>
    fun subscribe(): PublishSubject<String>
    fun unsubscribe(): PublishSubject<String>
    fun updateSeen(): PublishSubject<String>
}
