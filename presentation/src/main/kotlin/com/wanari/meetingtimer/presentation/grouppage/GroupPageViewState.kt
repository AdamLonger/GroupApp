package com.wanari.meetingtimer.presentation.grouppage

import model.GroupObject

data class GroupPageViewState(
        val loading: Boolean = false,
        val data: GroupObject? = null,
        val subPathSet: Boolean = false,
        val isSubscribed: Boolean = false,
        val seenUpdated: Boolean = false,
        val errorRes: Int? = null
)
