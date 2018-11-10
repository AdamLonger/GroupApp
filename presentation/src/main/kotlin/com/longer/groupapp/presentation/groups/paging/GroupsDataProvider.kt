package com.longer.groupapp.presentation.groups.paging

import com.longer.groupapp.presentation.utils.PagingProvider
import interactor.GroupsInteractor
import model.GroupObject

class GroupsDataProvider(
        groupsInteractor: GroupsInteractor
) : PagingProvider<GroupObject>(groupsInteractor)
