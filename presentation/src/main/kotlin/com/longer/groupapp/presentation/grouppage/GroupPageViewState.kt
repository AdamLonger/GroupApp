package com.longer.groupapp.presentation.grouppage

import model.GroupObject

data class GroupPageViewState(
        val loading: Boolean = false,
        val data: GroupObject? = null,
        val subPathSet: Boolean = false,
        val seenUpdated: Boolean = false,
        val hasChild: Boolean = true,
        val isSubscribed: Boolean = false,
        val errorRes: Int? = null
)
