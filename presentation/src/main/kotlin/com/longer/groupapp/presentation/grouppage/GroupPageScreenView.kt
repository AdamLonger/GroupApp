package com.longer.groupapp.presentation.grouppage

import com.longer.groupapp.common.mvi.MviView
import io.reactivex.subjects.PublishSubject

interface GroupPageScreenView : MviView<GroupPageViewState> {
    fun loadContent(): PublishSubject<String>
    fun setSubPath(): PublishSubject<String>
    fun subscribe(): PublishSubject<String>
    fun unsubscribe(): PublishSubject<String>
    fun updateSeen(): PublishSubject<String>
}
