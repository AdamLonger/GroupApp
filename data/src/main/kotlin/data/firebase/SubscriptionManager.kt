package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.wanari.meetingtimer.common.utils.TRIGGER
import data.utils.groupsPath
import data.utils.subscriptionsPath
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel

class SubscriptionManager(authManager: AuthManager,
                          private val database: FirebaseDatabase,
                          private val groupManager: GroupManager) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference
    private val invalidationSubject = PublishSubject.create<Any>()

    init {
        databaseRef.child(groupsPath()).addChildEventListener(SubscriptionsChildEventListener())
    }

    fun getItemChangeSubject(): PublishSubject<Any> = invalidationSubject

    fun getItems(count: Int): Single<List<GroupDataModel>> {
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
                                        groupManager.getItem(item)
                                    }
                            ).toList()
                        }
            }
        }
    }

    fun getItemsAfter(key: String, count: Int): Single<List<GroupDataModel>> {
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
                                        groupManager.getItem(item)
                                    }
                            ).toList()
                        }
            }
        }
    }

    fun getItemsBefore(key: String, count: Int): Single<List<GroupDataModel>> {
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
                                        groupManager.getItem(item)
                                    }
                            ).toList()
                        }
            }
        }
    }

    fun getItem(key: String): Single<GroupDataModel> {
        return Single.defer {
            val userid = authManager.getCurrentUserBlocking()?.uid
            userid?.let { uid ->
                return@defer RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath("$uid/$key")))
                        .flatMap {
                            groupManager.getItem(it.getValue(String::class.java)!!)
                        }
            }
        }
    }

    inner class SubscriptionsChildEventListener : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {}

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            invalidationSubject.onNext(TRIGGER)
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            invalidationSubject.onNext(TRIGGER)
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            invalidationSubject.onNext(TRIGGER)
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            invalidationSubject.onNext(TRIGGER)
        }

    }
}
