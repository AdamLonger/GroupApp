package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import com.longer.groupapp.common.utils.TRIGGER
import com.longer.groupapp.common.utils.toFirebaseString
import data.mapper.toObject
import data.utils.consts.DEFAULT_FIREBASE_DATE_TIME
import data.utils.subscriptionsPath
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import model.GroupObject
import org.threeten.bp.LocalDateTime
import java.util.*

class SubscriptionManager(authManager: AuthManager,
                          private val database: FirebaseDatabase,
                          private val groupManager: GroupManager,
                          private val seenManager: SeenManager) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference
    private val invalidationSubject = PublishSubject.create<Any>()

    private val mergedInvalidationSubject: Observable<Any> = Observable.merge(
            groupManager.getItemChangeSubject(),
            seenManager.getItemChangeSubject(),
            invalidationSubject)

    fun getItemChangeSubject(): Observable<Any> = mergedInvalidationSubject

    fun getItems(count: Int): Single<List<GroupObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath())
                .orderByKey()
                .limitToFirst(count))
                .map {
                    (it.value as? HashMap<String, String>)?.toList()?.sortedBy { it.first }
                }
                .flatMap { list ->
                    return@flatMap Single.merge(list
                            .map { item ->
                                groupManager.getItem(item.first).zipWith(
                                        seenManager.getItem(item.first)
                                ).map { pair ->
                                    pair.first.toObject().apply {
                                        isNotSeen = this.latestDate?.isAfter(pair.second) ?: false
                                    }
                                }
                            }
                    ).toList()
                }
    }

    fun getItemsAfter(key: String, count: Int): Single<List<GroupObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath())
                .orderByKey()
                .startAt(key)
                .limitToFirst(count))
                .map {
                    (it.value as? HashMap<String, String>)?.toList()?.sortedBy { it.first }
                }
                .flatMap { list ->
                    if (list.isEmpty()) return@flatMap Single.just(emptyList<GroupObject>())
                    return@flatMap Single.merge(list.drop(1)
                            .map { item ->
                                groupManager.getItem(item.first).zipWith(
                                        seenManager.getItem(item.first)
                                ).map { pair ->
                                    pair.first.toObject().apply {
                                        isNotSeen = this.latestDate?.isAfter(pair.second) ?: false
                                    }
                                }
                            }
                    ).toList()
                }
    }

    fun getItemsBefore(key: String, count: Int): Single<List<GroupObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath())
                .orderByKey()
                .endAt(key)
                .limitToLast(count))
                .map {
                    (it.value as? HashMap<String, String>)?.toList()?.sortedBy { it.first }
                }
                .flatMap { list ->
                    if (list.isEmpty()) return@flatMap Single.just(emptyList<GroupObject>())
                    return@flatMap Single.merge(list.dropLast(1)
                            .map { item ->
                                groupManager.getItem(item.first).zipWith(
                                        seenManager.getItem(item.first)
                                ).map { pair ->
                                    pair.first.toObject().apply {
                                        isNotSeen = this.latestDate?.isAfter(pair.second) ?: false
                                    }
                                }
                            }
                    ).toList()
                }

    }

    fun unsubscribe(key: String): Completable {
        return RxFirebaseDatabase.removeValue(
                databaseRef.child(subscriptionsPath(key)))
                .andThen(seenManager.removeKey(key))
                .onErrorComplete()
                .doOnComplete {
                    invalidationSubject.onNext(TRIGGER)
                }
    }

    fun subscribe(key: String): Completable {
        return RxFirebaseDatabase.setValue(
                databaseRef.child(subscriptionsPath(key)),
                LocalDateTime.now().toFirebaseString() ?: DEFAULT_FIREBASE_DATE_TIME)
                .andThen(seenManager.putValue(key))
                .onErrorComplete()
                .doOnComplete {
                    invalidationSubject.onNext(TRIGGER)
                }

    }

    fun isSubscribed(key: String): Single<Boolean> {
        return RxFirebaseDatabase.data(
                databaseRef.child(subscriptionsPath())
                        .orderByKey()
                        .equalTo(key))
                .map {
                    it.value != null
                }
    }

    fun updateSeen(key: String): Completable {
        return isSubscribed(key).flatMapCompletable {
            if (it) {
                return@flatMapCompletable seenManager.putValue(key)
            } else {
                return@flatMapCompletable Completable.complete()
            }
        }
    }
}
