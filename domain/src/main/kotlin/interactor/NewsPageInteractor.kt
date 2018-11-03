package interactor

import io.reactivex.Completable
import io.reactivex.Single
import model.NewsObject

interface NewsPageInteractor {
    fun getNewsContent(key: String): Single<NewsObject>
    fun setNewsSubPath(path:String): Completable
}
