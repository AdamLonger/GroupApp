package com.wanari.meetingtimer.presentation.signin

import android.content.Context
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.presentation.R
import interactor.SignInInteractor
import org.koin.android.ext.android.inject

class StartScreenFragment : ScreenFragment<StartScreenView, String>() {
    override val initialViewState = ""
    override val presenter by injectPresenter<StartPresenter>()

    override fun getTitle(context: Context): String = "Start Screen"

    override val layoutRes = R.layout.fragment_login

    val singInManager by inject<SignInInteractor>()
}