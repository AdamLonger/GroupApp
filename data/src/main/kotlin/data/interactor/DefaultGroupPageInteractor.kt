package data.interactor

import data.firebase.GroupManager
import data.firebase.NewsManager
import interactor.GroupPageInteractor
import io.reactivex.Completable
import io.reactivex.Single
import model.GroupDataModel

class DefaultGroupPageInteractor(
        private val groupManager: GroupManager,
        private val newsManager: NewsManager
) : GroupPageInteractor {

    override fun getGroupContent(key: String): Single<GroupDataModel> {
        return groupManager.getItem(key)
    }

    override fun setNewsSubPath(path: String): Completable {
        return newsManager.setSubPath(path)
    }
}
