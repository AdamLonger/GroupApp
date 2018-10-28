package data.interactor

import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import data.firebase.NewsManager
import interactor.NewsInteractor
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.NewsObject

class DefaultNewsInteractor(private val authManager: AuthManager,
                            private val newsManager: NewsManager,
                            private val database: FirebaseDatabase) : NewsInteractor {

    override fun getItemChangeSubject(): PublishSubject<Any> = newsManager.getItemChangeSubject()

    override fun getItems(count: Int): Single<List<NewsObject>> = newsManager.getItems(count)

    override fun getItemsAfter(key: String, count: Int): Single<List<NewsObject>>
            = newsManager.getItemsAfter(key,count)

    override fun getItemsBefore(key: String, count: Int): Single<List<NewsObject>>
            = newsManager.getItemsBefore(key,count)
}
