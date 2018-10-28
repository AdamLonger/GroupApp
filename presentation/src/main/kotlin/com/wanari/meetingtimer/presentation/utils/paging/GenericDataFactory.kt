package com.wanari.meetingtimer.presentation.utils.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import model.FirebaseObject

class GenericDataFactory<T: FirebaseObject>(
        private val DS: ItemKeyedDataSource<String, T>
): DataSource.Factory<String, T>() {

    private var datasourceLiveData = MutableLiveData<ItemKeyedDataSource<String, T>>()

    override fun create(): DataSource<String, T>? {
        val dataSource = DS::class.java.newInstance()
        datasourceLiveData.postValue(dataSource)
        return dataSource
    }
}