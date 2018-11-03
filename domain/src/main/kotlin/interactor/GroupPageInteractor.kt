package interactor

import io.reactivex.Completable
import io.reactivex.Single
import model.GroupDataModel

interface GroupPageInteractor {
    fun getGroupContent(key: String): Single<GroupDataModel>
    fun setNewsSubPath(path:String): Completable
}
