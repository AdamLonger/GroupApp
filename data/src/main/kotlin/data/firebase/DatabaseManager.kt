package data.firebase

import com.google.firebase.database.FirebaseDatabase
import exception.NotAuthenticatedException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

abstract class DatabaseManager(
        val authManager: AuthManager,
        val firebaseDatabase: FirebaseDatabase) {

    fun databaseGoOffline() {
        firebaseDatabase.goOffline()
    }

    fun Completable.guard(): Completable {
        return authManager.isAuthenticated().firstOrError()
                .onErrorResumeNext {
                    Single.error(NotAuthenticatedException(it))
                }.flatMapCompletable {
                    if (it) {
                        return@flatMapCompletable this
                    } else {
                        return@flatMapCompletable Completable.complete()
                    }
                }
    }

    fun <T> Single<T>.guard(): Single<T> {
        return authManager.isAuthenticated().firstOrError()
                .onErrorResumeNext {
                    Single.error(NotAuthenticatedException(it))
                }.flatMap {
                    if (it) {
                        return@flatMap this
                    } else {
                        return@flatMap Single.never<T>()
                    }
                }
    }

    fun <T> Observable<T>.guard(): Observable<T> {
        return authManager.isAuthenticated().firstOrError()
                .onErrorResumeNext {
                    Single.error(NotAuthenticatedException(it))
                }.flatMapObservable {
                    if (it) {
                        return@flatMapObservable this
                    } else {
                        return@flatMapObservable Observable.empty<T>()
                    }
                }
    }
}
