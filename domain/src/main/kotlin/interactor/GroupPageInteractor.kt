package interactor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import model.GroupObject

interface GroupPageInteractor {
    fun getGroupContent(key: String): Single<GroupObject>
    fun setNewsSubPath(key: String): Single<Boolean>
    fun getSubscriptionState(key: String): Observable<Boolean>
    fun subscribe(key: String): Completable
    fun unsubscribe(key: String): Completable
    fun updateSeen(key: String): Completable
}
