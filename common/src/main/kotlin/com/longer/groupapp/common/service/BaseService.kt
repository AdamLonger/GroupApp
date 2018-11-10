package com.longer.groupapp.common.service

import android.app.Service
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseService : Service() {

    private val disposables = CompositeDisposable()

    @CallSuper
    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    protected fun Disposable.autoClear() = disposables.add(this)
}
