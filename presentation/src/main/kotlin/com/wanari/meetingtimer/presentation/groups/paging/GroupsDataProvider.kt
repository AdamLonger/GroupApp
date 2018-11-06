package com.wanari.meetingtimer.presentation.groups.paging

import com.wanari.meetingtimer.presentation.utils.PagingProvider
import interactor.GroupsInteractor
import model.GroupObject

class GroupsDataProvider(
        groupsInteractor: GroupsInteractor
) : PagingProvider<GroupObject>(groupsInteractor)
