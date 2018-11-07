package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import data.mapper.toObject
import data.utils.subscriptionsPath
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import model.GroupObject

class SubscriptionManager(authManager: AuthManager,
                          private val database: FirebaseDatabase,
                          private val groupManager: GroupManager,
                          private val seenManager: SeenManager) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference
    private val invalidationSubject: Observable<Any> = PublishSubject.merge(
            groupManager.getItemChangeSubject(),
            seenManager.getItemChangeSubject())

    fun getItemChangeSubject(): Observable<Any> = invalidationSubject

    fun getItems(count: Int): Single<List<GroupObject>> {
        return Single.defer {
            val userid = authManager.getCurrentUserBlocking()?.uid
            userid?.let { uid ->
                RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath(uid))
                        .orderByKey()
                        .limitToFirst(count))
                        .map { it.value as ArrayList<String> }
                        .flatMap {
                            Single.merge(it
                                    .map { item ->
                                        groupManager.getItem(item).zipWith(
                                                seenManager.getItem(item)
                                        ).map { pair ->
                                            pair.first.toObject().apply {
                                                isNotSeen = this.latestDate?.isAfter(pair.second) ?: false
                                            }
                                        }
                                    }
                            ).toList()
                        }
            }
        }
    }

    fun getItemsAfter(key: String, count: Int): Single<List<GroupObject>> {
        return Single.defer {
            val userid = authManager.getCurrentUserBlocking()?.uid
            userid?.let { uid ->
                RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath(uid))
                        .orderByKey()
                        .startAt(key)
                        .limitToFirst(count))
                        .map { it.value as ArrayList<String> }
                        .flatMap {
                            Single.merge(it
                                    .map { item ->
                                        groupManager.getItem(item).zipWith(
                                                seenManager.getItem(item)
                                        ).map { pair ->
                                            pair.first.toObject().apply {
                                                isNotSeen = this.latestDate?.isBefore(pair.second) ?: false
                                            }
                                        }
                                    }
                            ).toList()
                        }
            }
        }
    }

    fun getItemsBefore(key: String, count: Int): Single<List<GroupObject>> {
        return Single.defer {
            val userid = authManager.getCurrentUserBlocking()?.uid
            userid?.let { uid ->
                RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath(uid))
                        .orderByKey()
                        .startAt(key)
                        .limitToLast(count))
                        .map { it.value as ArrayList<String> }
                        .flatMap {
                            Single.merge(it
                                    .map { item ->
                                        groupManager.getItem(item).zipWith(
                                                seenManager.getItem(item)
                                        ).map { pair ->
                                            pair.first.toObject().apply {
                                                isNotSeen = this.latestDate?.isBefore(pair.second) ?: false
                                            }
                                        }
                                    }
                            ).toList()
                        }
            }
        }
    }

    fun unsubscribe(key: String): Completable {
        val userid = authManager.getCurrentUserBlocking()?.uid
        userid?.let { uid ->
            RxFirebaseDatabase.data(
                    databaseRef.child(subscriptionsPath("$uid/"))
                            .equalTo(key))
                    .flatMapCompletable { snapshot ->
                        RxFirebaseDatabase.removeValue(snapshot.ref)
                    }
        }
        return Completable.complete()
    }

    fun subscribe(key: String): Completable {
        val userid = authManager.getCurrentUserBlocking()?.uid
        userid?.let { uid ->
            return RxFirebaseDatabase.setValue(
                    databaseRef.child(subscriptionsPath("$uid/")).push(), key)
        }
        return Completable.complete()
    }

    fun isSubscribed(key: String): Single<Boolean> {
        val userid = authManager.getCurrentUserBlocking()?.uid
        userid?.let { uid ->
            ITTAPARA
            return RxFirebaseDatabase.data(
                    databaseRef.child(subscriptionsPath("$uid/"))
                            .equalTo(key)).map { it.exists() }
        }
        return Single.just(false)
    }
}
