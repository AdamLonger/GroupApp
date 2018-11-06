package interactor

import io.reactivex.Completable
import model.NewsObject

interface NewsInteractor : PagingSourceInteractor<NewsObject> {
    fun setSubPath(path: String): Completable
}
