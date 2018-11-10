package com.longer.groupapp.presentation.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import interactor.PagingSourceInteractor
import model.FirebaseObject

class PagingDataFactory<T : FirebaseObject>(
        private val interactor: PagingSourceInteractor<T>
) : DataSource.Factory<String, T>() {

    private var datasourceLiveData = MutableLiveData<PagingDataSource<T>>()

    override fun create(): PagingDataSource<T>? {
        val dataSource = PagingDataSource(interactor)
        datasourceLiveData.postValue(dataSource)
        return dataSource
    }
}