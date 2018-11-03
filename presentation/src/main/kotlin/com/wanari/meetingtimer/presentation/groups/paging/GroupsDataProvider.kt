package com.wanari.meetingtimer.presentation.groups.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanari.meetingtimer.presentation.model.GroupObject
import interactor.GroupsInteractor
import model.GroupDataModel

private const val PAGE_SIZE = 4

class GroupsDataProvider(groupsInteractor: GroupsInteractor) {
    private var itemDataFactory = GroupsDataFactory(groupsInteractor)

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
