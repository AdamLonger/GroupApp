package com.wanari.meetingtimer.presentation.usergroups.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanari.meetingtimer.presentation.model.GroupObject
import interactor.UserGroupsInteractor

class UserGroupsDataFactory(private val groupsInteractor: UserGroupsInteractor) : DataSource.Factory<String, GroupObject>() {

    private var datasourceLiveData = MutableLiveData<UserGroupsDataSource>()

    override fun create(): UserGroupsDataSource? {
        val dataSource = UserGroupsDataSource(groupsInteractor)
        datasourceLiveData.postValue(dataSource)
        return dataSource
    }
}