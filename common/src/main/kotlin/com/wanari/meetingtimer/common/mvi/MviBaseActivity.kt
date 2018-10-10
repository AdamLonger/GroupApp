package com.wanari.meetingtimer.common.mvi

import android.os.Bundle
import android.support.annotation.LayoutRes
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import org.koin.android.ext.android.get
import org.koin.core.parameter.ParameterList
import org.koin.core.scope.Scope

abstract class MviBaseActivity<V : MviView<VS>, VS : Any> : MviActivity<V, MviPresenter<V, VS>>() {

    abstract val layoutRes: Int
        @LayoutRes get

    abstract val initialViewState: VS
    abstract val presenter: MviPresenter<V, VS>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun createPresenter(): MviPresenter<V, VS> = presenter

    inline fun <reified P : MviPresenter<V, VS>> injectPresenter(
            name: String = "",
            scope: Scope? = null
    ) = lazy { get<P>(name, scope) { ParameterList(initialViewState) } }
}
