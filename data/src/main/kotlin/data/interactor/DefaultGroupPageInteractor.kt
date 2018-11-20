package data.interactor

import data.firebase.GroupManager
import data.firebase.NewsManager
import data.firebase.SubscriptionManager
import data.mapper.toObject
import interactor.GroupPageInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import model.GroupObject

class DefaultGroupPageInteractor(
        private val groupManager: GroupManager,
        private val subscriptionManager: SubscriptionManager,
        private val newsManager: NewsManager
) : GroupPageInteractor {
    override fun getGroupContent(key: String): Single<GroupObject> {
        return groupManager.getItem(key).map { it.toObject() }
    }

    override fun getSubscriptionState(key: String): Observable<Boolean> {
        return subscriptionManager.isSubscribed(key)
    }

    override fun setNewsSubPath(key: String): Single<Boolean> {
        return newsManager.setSubPath(key)
    }

    override fun subscribe(key: String): Completable {
        return subscriptionManager.subscribe(key)
    }

    override fun unsubscribe(key: String): Completable {
        return subscriptionManager.unsubscribe(key)
    }

    override fun updateSeen(key: String): Completable {
        return subscriptionManager.updateSeen(key)
    }
}
