package data.utils.Query

import data.firebase.DatabaseManager
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class QueryHolder<T>(authChange: Observable<Boolean>, authenticationNeeded: Boolean = true, private val queryObservable: (Boolean) -> Observable<T>) {

    private val internalSubject = BehaviorSubject.create<T>()
    private val internalDisposable: Disposable

    init {
        internalDisposable = authChange
                .filter { !authenticationNeeded || it }
                .switchMap {
                    Observable.defer { queryObservable(it) }
                }.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe({ internalSubject.onNext(it) }, { Timber.e(it) })
    }

    fun query(): Observable<T> = internalSubject
            .observeOn(Schedulers.computation())

    fun dispose(): Unit = internalDisposable.dispose()
}

fun <T> DatabaseManager.queryHolder(
        authChange: Observable<Boolean>,
        authenticationNeeded: Boolean = true,
        queryObservable: (Boolean) -> Observable<T>
) = kotlin.lazy {
    QueryHolder(authChange, authenticationNeeded, queryObservable)
}