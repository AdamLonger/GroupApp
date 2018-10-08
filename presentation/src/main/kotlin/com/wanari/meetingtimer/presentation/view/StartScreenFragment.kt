package com.wanari.meetingtimer.presentation.view

import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.presentation.R
import managers.LoginManager
import org.koin.android.ext.android.inject

class StartScreenFragment : ScreenFragment() {
    override val layoutRes = R.layout.fragment_login

    val loginManager by inject<LoginManager>()
}