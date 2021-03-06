package com.longer.groupapp.common.ui

import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()

    @CallSuper
    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    protected fun Disposable.disposeOnDestroy() = disposables.add(this)
}
