package com.wanari.meetingtimer.presentation.login

data class LogInViewState(
        val loading: Boolean = false,
        val errorRes: Int? = null,
        val uiLocked: Boolean = false,
        val forward:Boolean = false
)