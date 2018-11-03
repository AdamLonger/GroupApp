package com.wanari.meetingtimer.presentation.groups.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanari.meetingtimer.presentation.model.GroupObject
import interactor.GroupsInteractor
import model.GroupDataModel

class GroupsDataFactory(private val groupsInteractor: GroupsInteractor) : DataSource.Factory<String, GroupObject>() {

    private var datasourceLiveData = MutableLiveData<GroupsDataSource>()

    override fun create(): GroupsDataSource? {
        val dataSource = GroupsDataSource(groupsInteractor)
        datasourceLiveData.postValue(dataSource)
        return dataSource
    }
}