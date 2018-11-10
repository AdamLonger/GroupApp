package com.longer.groupapp.presentation.groups

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import interactor.GroupsInteractor
import io.reactivex.Observable

class GroupsPresenter(initialState: GroupsViewState, private val groupsInteractor: GroupsInteractor) : BasePresenter<GroupsScreenView, GroupsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<GroupsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<GroupsViewState>>>()

        return result
    }

}
