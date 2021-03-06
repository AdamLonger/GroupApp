package interactor

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface PagingSourceInteractor<T> {
    fun getItemChangeSubject(): Observable<Any>
    fun getItems(count: Int): Single<List<T>>
    fun getItemsAfter(key: String, count: Int): Single<List<T>>
    fun getItemsBefore(key: String, count: Int): Single<List<T>>
}