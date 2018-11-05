package data.firebase

import android.util.Log
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.wanari.meetingtimer.common.utils.TRIGGER
import data.mapper.getArrayValue
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
        databaseRef.child(groupsPath()).addChildEventListener(object : ChildEventListener {
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
        })
    }

    fun getItemChangeSubject(): PublishSubject<Any> = invalidationSubject

    fun getItems(count: Int): Single<List<GroupDataModel>> {
        return Single.defer {
            val userid = authManager.getCurrentUserBlocking()?.uid
            userid?.let { uid ->
                RxFirebaseDatabase.data(databaseRef.child(subscriptionsPath(uid))
                        .orderByKey()
                        .limitToFirst(count))
                        .map {
                            (it.value as ArrayList<String>)
                                    .toList()
                                    .map {item ->
                                        Log.v("LONGLOG", "Item: $item")
                                        groupManager.getItem(item).blockingGet() }
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
                        .map {
                            NABAJVAN
                            it.getArrayValue(String::class.java)
                        }.map { list ->
                            list.map { groupManager.getItem(it).blockingGet() }
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
                        .map {
                            it.getArrayValue(String::class.java)
                        }.map { list ->
                            list.map { groupManager.getItem(it).blockingGet() }
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
}
