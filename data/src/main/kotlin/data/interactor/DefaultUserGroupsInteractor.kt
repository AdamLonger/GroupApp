package data.interactor

import data.firebase.SubscriptionManager
import interactor.UserGroupsInteractor
import io.reactivex.Observable
import io.reactivex.Single
import model.GroupObject

class DefaultUserGroupsInteractor(
        private val subscriptionManager: SubscriptionManager
) : UserGroupsInteractor {

    override fun getItemChangeSubject(): Observable<Any>
            = subscriptionManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<GroupObject>>
            = subscriptionManager.getItems(count)

    override fun getItemsAfter(key: String, count: Int): Single<List<GroupObject>>
            = subscriptionManager.getItemsAfter(key, count)

    override fun getItemsBefore(key: String, count: Int): Single<List<GroupObject>>
            = subscriptionManager.getItemsBefore(key, count)
}
