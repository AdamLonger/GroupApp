package com.longer.groupapp.presentation.signup

data class SignUpViewState(
        val loading: Boolean = false,
        val errorRes: Int? = null,
        val uiLocked: Boolean = false,
        val forward: Boolean = false
)