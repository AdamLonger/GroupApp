package com.wanari.meetingtimer.common.mvi

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import org.koin.android.ext.android.get
import org.koin.core.parameter.ParameterList
import org.koin.core.scope.Scope

abstract class MviBaseFragment<V : MviView<VS>, VS : Any> : MviFragment<V, MviPresenter<V, VS>>() {

    abstract val layoutRes: Int
        @LayoutRes get

    abstract val initialViewState: VS
    abstract val presenter: MviPresenter<V, VS>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun createPresenter(): MviPresenter<V, VS> = presenter

    abstract fun getTitle(context: Context): String

    inline fun <reified P : MviPresenter<V, VS>> injectPresenter(
            name: String = "",
            scope: Scope? = null
    ) = lazy { get<P>(name, scope) { ParameterList(initialViewState) } }
}
