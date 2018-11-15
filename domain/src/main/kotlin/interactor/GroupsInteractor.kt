package interactor

import io.reactivex.Single
import model.GroupObject

interface GroupsInteractor : PagingSourceInteractor<GroupObject>{
    fun hasChild(): Single<Boolean>
}
