package interactor

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.NewsObject

interface NewsInteractor {
    fun getItemChangeSubject(): PublishSubject<Any>
    fun getItems(count: Int): Single<List<NewsObject>>
    fun getItemsAfter(key: String, count: Int): Single<List<NewsObject>>
    fun getItemsBefore(key: String, count: Int): Single<List<NewsObject>>
}
