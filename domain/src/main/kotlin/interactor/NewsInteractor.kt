package interactor

import io.reactivex.Single
import model.NewsObject

interface NewsInteractor : PagingSourceInteractor<NewsObject> {
    fun setSubPath(path: String): Single<Boolean>
}
