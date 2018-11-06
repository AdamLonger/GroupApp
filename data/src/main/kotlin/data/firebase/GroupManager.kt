package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.wanari.meetingtimer.common.utils.TRIGGER
import data.mapper.getArrayValue
import data.utils.groupsPath
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel

class GroupManager(authManager: AuthManager, private val database: FirebaseDatabase) :
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
        return RxFirebaseDatabase.data(databaseRef.child(groupsPath()).orderByKey().limitToFirst(count))
                .map {
                    it.getArrayValue(GroupDataModel::class.java)
                }
    }

    fun getItemsAfter(key: String, count: Int): Single<List<GroupDataModel>> {
        return RxFirebaseDatabase.data(databaseRef.child(groupsPath()).orderByKey().startAt(key).limitToFirst(count))
                .map {
                    it.getArrayValue(GroupDataModel::class.java).drop(1)
                }
    }

    fun getItemsBefore(key: String, count: Int): Single<List<GroupDataModel>> {
        return RxFirebaseDatabase.data(databaseRef.child(groupsPath()).orderByKey().endAt(key).limitToLast(count))
                .map {
                    it.getArrayValue(GroupDataModel::class.java).dropLast(1)
                }
    }

    fun getItem(key: String): Single<GroupDataModel> {
        return RxFirebaseDatabase.data(databaseRef.child(groupsPath(key)))
                .map {
                    it.getValue(GroupDataModel::class.java).apply {
                        this?.objectKey = key
                    }
                }
    }
}
