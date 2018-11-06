package com.wanari.meetingtimer.presentation.usergroups.paging

import com.wanari.meetingtimer.presentation.utils.PagingProvider
import interactor.UserGroupsInteractor
import model.GroupObject

class UserGroupsDataProvider(
        groupsInteractor: UserGroupsInteractor
) : PagingProvider<GroupObject>(groupsInteractor)