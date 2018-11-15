package data.interactor

import data.firebase.GroupManager
import data.firebase.NewsManager
import data.firebase.SubscriptionManager
import data.mapper.toObject
import interactor.GroupPageInteractor
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import model.GroupObject

class DefaultGroupPageInteractor(
        private val groupManager: GroupManager,
        private val subscriptionManager: SubscriptionManager,
        private val newsManager: NewsManager
) : GroupPageInteractor {
    override fun getGroupContent(key: String): Single<GroupObject> {
        return groupManager.getItem(key).zipWith(
                subscriptionManager.isSubscribed(key)
        ).map { pair -> pair.first.toObject().apply { isSubscribed = pair.second } }
    }

    override fun setNewsSubPath(path: String): Single<Boolean> {
        return newsManager.setSubPath(path)
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
