package interactor

import io.reactivex.Single
import model.NewsObject

interface NewsPageInteractor {
    fun getNewsContent(key: String): Single<NewsObject>
}
