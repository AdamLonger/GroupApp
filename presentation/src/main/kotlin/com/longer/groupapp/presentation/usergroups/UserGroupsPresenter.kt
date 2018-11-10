package com.longer.groupapp.presentation.usergroups

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import interactor.UserGroupsInteractor
import io.reactivex.Observable

class UserGroupsPresenter(initialState: UserGroupsViewState, private val groupsInteractor: UserGroupsInteractor) : BasePresenter<UserGroupsScreenView, UserGroupsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<UserGroupsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<UserGroupsViewState>>>()

        return result
    }

}
