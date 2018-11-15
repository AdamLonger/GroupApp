package data.interactor

import data.firebase.GroupManager
import data.mapper.toObject
import interactor.GroupsInteractor
import io.reactivex.Observable
import io.reactivex.Single
import model.GroupObject

class DefaultGroupsInteractor(
        private val groupManager: GroupManager
) : GroupsInteractor {
    override fun getItemChangeSubject(): Observable<Any> = groupManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<GroupObject>> =
            groupManager.getItems(count).map { items ->
                items.map {
                    it.toObject()
                }
            }

    override fun getItemsAfter(key: String, count: Int): Single<List<GroupObject>> =
            groupManager.getItemsAfter(key, count).map { items ->
                items.map {
                    it.toObject()
                }
            }

    override fun getItemsBefore(key: String, count: Int): Single<List<GroupObject>> =
            groupManager.getItemsBefore(key, count).map { items ->
                items.map {
                    it.toObject()
                }
            }

    override fun hasChild(): Single<Boolean> = groupManager.hasChild()
}
