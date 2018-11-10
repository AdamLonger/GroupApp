package com.longer.groupapp.presentation.usergroups.paging

import com.longer.groupapp.presentation.utils.PagingProvider
import interactor.UserGroupsInteractor
import model.GroupObject

class UserGroupsDataProvider(
        groupsInteractor: UserGroupsInteractor
) : PagingProvider<GroupObject>(groupsInteractor)