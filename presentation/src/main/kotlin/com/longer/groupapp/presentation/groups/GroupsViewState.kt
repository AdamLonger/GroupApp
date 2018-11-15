package com.longer.groupapp.presentation.groups

data class GroupsViewState(
        val loading: Boolean = false,
        val hasChild: Boolean = true,
        val errorRes: Int? = null
)