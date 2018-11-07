package data.interactor

import data.firebase.NewsManager
import interactor.NewsInteractor
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.NewsObject

class DefaultNewsInteractor(
        private val newsManager: NewsManager
) : NewsInteractor {
    override fun getItemChangeSubject(): Observable<Any> = newsManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<NewsObject>> = newsManager.getItems(count)

    override fun getItemsAfter(key: String, count: Int): Single<List<NewsObject>> = newsManager.getItemsAfter(key, count)

    override fun getItemsBefore(key: String, count: Int): Single<List<NewsObject>> = newsManager.getItemsBefore(key, count)

    override fun setSubPath(path: String) = newsManager.setSubPath(path)
}
