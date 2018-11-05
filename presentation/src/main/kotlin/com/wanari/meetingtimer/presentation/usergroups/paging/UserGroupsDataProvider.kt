package com.wanari.meetingtimer.presentation.usergroups.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanari.meetingtimer.presentation.model.GroupObject
import interactor.UserGroupsInteractor

private const val PAGE_SIZE = 4

class UserGroupsDataProvider(groupsInteractor: UserGroupsInteractor) {
    private var itemDataFactory = UserGroupsDataFactory(groupsInteractor)

    fun getItems(): LiveData<PagedList<GroupObject>>? {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        return LivePagedListBuilder(itemDataFactory, config)
                .setInitialLoadKey("")
                .build()
    }
}
