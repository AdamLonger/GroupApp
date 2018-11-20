package com.longer.groupapp.presentation.grouppage

import com.longer.groupapp.common.mvi.MviView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface GroupPageScreenView : MviView<GroupPageViewState> {
    fun setSubPath(): Observable<String>
    fun subscribe(): Observable<String>
    fun unsubscribe(): Observable<String>
}
