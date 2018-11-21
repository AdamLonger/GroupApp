package com.longer.groupapp.presentation.grouppage

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.presentation.R
import interactor.GroupPageInteractor
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GroupPagePresenter(initialState: GroupPageViewState, private val interactor: GroupPageInteractor) : BasePresenter<GroupPageScreenView, GroupPageViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<GroupPageViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<GroupPageViewState>>>()

        result.add(intent { view -> view.setSubPath() }
                .flatMap { key ->
                    Single.concat(listOf(
                            interactor.setNewsSubPath(key)
                                    .map { GroupPageViewStateChanges.SubPathInited(it) },
                            interactor.updateSeen(key)
                                    .toSingle { GroupPageViewStateChanges.SeenUpdated() },
                            interactor.getGroupContent(key)
                                    .map { GroupPageViewStateChanges.DataLoaded(it) }
                    )).toObservable()
                            .onErrorReturn { GroupPageViewStateChanges.Error(R.string.message_error) }
                            .startWith(GroupPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(intent { view -> view.setSubPath() }
                .switchMap {
                    interactor.getSubscriptionState(it)
                            .mapViewStateChange { sub ->
                                if (sub) {
                                    GroupPageViewStateChanges.Subscribed()
                                } else {
                                    GroupPageViewStateChanges.Unsubscribed()
                                }
                            }
                            .onErrorReturn { GroupPageViewStateChanges.Error(R.string.message_error) }
                            .subscribeOn(Schedulers.io())
                }
        )

        result.add(intent { view -> view.subscribe() }
                .flatMap { key ->
                    interactor.subscribe(key)
                            .mapViewStateChange { GroupPageViewStateChanges.StopLoading() }
                            .onErrorReturn { GroupPageViewStateChanges.Error(R.string.message_error) }
                            .startWith(GroupPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(intent { view -> view.unsubscribe() }
                .flatMap { key ->
                    interactor.unsubscribe(key)
                            .mapViewStateChange { GroupPageViewStateChanges.StopLoading() }
                            .onErrorReturn { GroupPageViewStateChanges.Error(R.string.message_error) }
                            .startWith(GroupPageViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(AppStateManager.getNetworkState()
                .toObservable()
                .mapViewStateChange {
                    GroupPageViewStateChanges.LockUI(!it)
                }
                .onErrorReturn { GroupPageViewStateChanges.Error(R.string.message_error) }
                .subscribeOn(Schedulers.io()))

        return result
    }

}
