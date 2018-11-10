package com.longer.groupapp.common.ui

import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {

    private val disposables = CompositeDisposable()

    @CallSuper
    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    protected fun Disposable.disposeOnDestroy() = disposables.add(this)
}
