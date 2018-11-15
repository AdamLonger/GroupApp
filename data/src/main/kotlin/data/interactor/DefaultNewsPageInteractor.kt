package data.interactor

import data.firebase.NewsManager
import interactor.NewsPageInteractor
import io.reactivex.Completable
import io.reactivex.Single
import model.NewsObject

class DefaultNewsPageInteractor(
        private val newsManager: NewsManager
) : NewsPageInteractor {

    override fun getNewsContent(key: String): Single<NewsObject> {
        return newsManager.getItem(key)
    }

    override fun setNewsSubPath(path: String): Single<Boolean> {
        return newsManager.setSubPath(path)
    }
}
