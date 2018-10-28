package com.wanari.meetingtimer.presentation.utils.paging

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PagedList
import model.FirebaseObject

class GenericPagedViewModel<T:FirebaseObject>(
        private val DS: ItemKeyedDataSource<String, T>
) : ViewModel() {
    private val provider: GenericDataProvider<T>? = GenericDataProvider(DS)
 
    fun getItems(): LiveData<PagedList<T>>? {
        return provider?.getItems()
    }
}