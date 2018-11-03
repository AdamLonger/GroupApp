package data.interactor

import data.firebase.GroupManager
import interactor.GroupsInteractor
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel

class DefaultGroupsInteractor(
        private val groupManager: GroupManager
) : GroupsInteractor {

    override fun getItemChangeSubject(): PublishSubject<Any> = groupManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<GroupDataModel>> = groupManager.getItems(count)

    override fun getItemsAfter(key: String, count: Int): Single<List<GroupDataModel>> = groupManager.getItemsAfter(key, count)

    override fun getItemsBefore(key: String, count: Int): Single<List<GroupDataModel>> = groupManager.getItemsBefore(key, count)
}
