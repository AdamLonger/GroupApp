package com.wanari.meetingtimer.presentation.grouppage

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.presentation.R
import interactor.GroupPageInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GroupPagePresenter(initialState: GroupPageViewState, private val interactor: GroupPageInteractor) : BasePresenter<GroupPageScreenView, GroupPageViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<GroupPageViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<GroupPageViewState>>>()

        result.add(intent { view -> view.setSubPath() }
                .flatMap { path ->
                    interactor.setNewsSubPath(path)
                            .mapViewStateChange { GroupPageViewStateChanges.SubPathInited() }
                            .onErrorReturn { error -> GroupPageViewStateChanges.Error(R.string.message_error) }
                            .startWith(GroupPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(intent { view -> view.loadContent() }
                .flatMap { key ->
                    interactor.getGroupContent(key)
                            .mapViewStateChange { GroupPageViewStateChanges.DataLoaded(it) }
                            .onErrorReturn { error -> GroupPageViewStateChanges.Error(R.string.message_error) }
                            .startWith(GroupPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        return result
    }

}
