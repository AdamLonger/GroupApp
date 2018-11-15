package com.longer.groupapp.presentation.groups

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import com.longer.groupapp.presentation.R
import interactor.GroupsInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GroupsPresenter(initialState: GroupsViewState, private val groupsInteractor: GroupsInteractor) : BasePresenter<GroupsScreenView, GroupsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<GroupsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<GroupsViewState>>>()

        result.add(groupsInteractor.hasChild()
                .mapViewStateChange { GroupsViewStateChanges.ChildCounted(it) }
                .onErrorReturn { GroupsViewStateChanges.Error(R.string.message_error) }
                .startWith(GroupsViewStateChanges.Loading())
                .subscribeOn(Schedulers.io())
        )

        return result
    }

}
