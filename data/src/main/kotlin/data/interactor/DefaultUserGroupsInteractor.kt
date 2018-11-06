package data.interactor

import data.firebase.SubscriptionManager
import data.mapper.toObject
import interactor.UserGroupsInteractor
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel
import model.GroupObject

class DefaultUserGroupsInteractor(
        private val subscriptionManager: SubscriptionManager
) : UserGroupsInteractor {

    override fun getItemChangeSubject(): PublishSubject<Any> = subscriptionManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<GroupObject>> = subscriptionManager.getItems(count).map { items -> items.map { it.toObject()} }

    override fun getItemsAfter(key: String, count: Int): Single<List<GroupObject>> = subscriptionManager.getItemsAfter(key, count).map { items -> items.map { it.toObject()} }

    override fun getItemsBefore(key: String, count: Int): Single<List<GroupObject>> = subscriptionManager.getItemsBefore(key, count).map { items -> items.map { it.toObject()} }
}
