package interactor

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel

interface GroupsInteractor {
    fun getItemChangeSubject(): PublishSubject<Any>
    fun getItems(count: Int): Single<List<GroupDataModel>>
    fun getItemsAfter(key: String, count: Int): Single<List<GroupDataModel>>
    fun getItemsBefore(key: String, count: Int): Single<List<GroupDataModel>>
}
