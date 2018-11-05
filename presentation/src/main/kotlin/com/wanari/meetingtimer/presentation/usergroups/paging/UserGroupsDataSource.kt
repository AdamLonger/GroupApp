package com.wanari.meetingtimer.presentation.usergroups.paging

import android.arch.paging.ItemKeyedDataSource
import com.wanari.meetingtimer.presentation.model.GroupObject
import com.wanari.meetingtimer.presentation.model.parse
import interactor.UserGroupsInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import util.toOptional

class UserGroupsDataSource(private val groupsInteractor: UserGroupsInteractor) : ItemKeyedDataSource<String, GroupObject>() {

    private val disposables = CompositeDisposable()

    init {
        disposables.add(
                groupsInteractor.getItemChangeSubject()
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.computation())
                        .subscribe {
                            invalidate()
                            disposables.clear()
                        }
        )
    }

    override fun loadInitial(params: ItemKeyedDataSource.LoadInitialParams<String>, callback: ItemKeyedDataSource.LoadInitialCallback<GroupObject>) {
        disposables.add(
                groupsInteractor.getItems(params.requestedLoadSize).subscribe({
                    callback.onResult( it.map{item -> GroupObject().parse(item.toOptional())})
                }, {})
        )
    }

    override fun loadAfter(params: ItemKeyedDataSource.LoadParams<String>, callback: ItemKeyedDataSource.LoadCallback<GroupObject>) {
        disposables.add(
                groupsInteractor.getItemsAfter(params.key, params.requestedLoadSize).subscribe({
                    callback.onResult(it.map{item -> GroupObject().parse(item.toOptional())})
                }, {})
        )
    }

    override fun loadBefore(params: ItemKeyedDataSource.LoadParams<String>, callback: ItemKeyedDataSource.LoadCallback<GroupObject>) {
        disposables.add(
                groupsInteractor.getItemsBefore(params.key, params.requestedLoadSize).subscribe({
                    callback.onResult(it.map{item -> GroupObject().parse(item.toOptional())})
                }, {})
        )
    }

    override fun getKey(item: GroupObject): String {
        return item.key ?: ""
    }
}
