package com.wanari.meetingtimer.presentation.signup

data class SignUpViewState(
        val loading: Boolean = false,
        val errorRes: Int? = null,
        val forward: Boolean = false
)