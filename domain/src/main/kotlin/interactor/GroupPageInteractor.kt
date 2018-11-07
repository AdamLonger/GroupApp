package interactor

import io.reactivex.Completable
import io.reactivex.Single
import model.GroupObject

interface GroupPageInteractor {
    fun getGroupContent(key: String): Single<GroupObject>
    fun setNewsSubPath(path:String): Completable
    fun subscribe(key:String):Completable
    fun unsubscribe(key:String):Completable
}
