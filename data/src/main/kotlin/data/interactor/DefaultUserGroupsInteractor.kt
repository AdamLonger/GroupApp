package data.interactor

import data.firebase.SubscriptionManager
import interactor.UserGroupsInteractor
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel

class DefaultUserGroupsInteractor(
        private val subscriptionManager: SubscriptionManager
) : UserGroupsInteractor {

    override fun getItemChangeSubject(): PublishSubject<Any> = subscriptionManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<GroupDataModel>> = subscriptionManager.getItems(count)

    override fun getItemsAfter(key: String, count: Int): Single<List<GroupDataModel>> = subscriptionManager.getItemsAfter(key, count)

    override fun getItemsBefore(key: String, count: Int): Single<List<GroupDataModel>> = subscriptionManager.getItemsBefore(key, count)
}
