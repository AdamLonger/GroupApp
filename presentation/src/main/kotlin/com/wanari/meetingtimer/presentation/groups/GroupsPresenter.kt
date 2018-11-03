package com.wanari.meetingtimer.presentation.groups

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.presentation.news.NewsScreenView
import com.wanari.meetingtimer.presentation.news.NewsViewState
import interactor.GroupsInteractor
import interactor.NewsInteractor
import io.reactivex.Observable

class GroupsPresenter(initialState: GroupsViewState, private val groupsInteractor: GroupsInteractor) : BasePresenter<GroupsScreenView, GroupsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<GroupsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<GroupsViewState>>>()

        return result
    }

}
