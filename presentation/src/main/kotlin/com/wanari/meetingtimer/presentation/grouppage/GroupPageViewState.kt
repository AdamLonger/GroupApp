package com.wanari.meetingtimer.presentation.grouppage

import model.GroupDataModel

data class GroupPageViewState(
        val loading: Boolean = false,
        val data: GroupDataModel? = null,
        val subPathSet: Boolean = false,
        val errorRes: Int? = null
)
