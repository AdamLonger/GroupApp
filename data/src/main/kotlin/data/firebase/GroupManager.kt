package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import data.mapper.getArrayValue
import data.utils.groupsPath
import data.utils.listeners.FirebaseChildEventListener
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.GroupDataModel

class GroupManager(authManager: AuthManager,
                   private val database: FirebaseDatabase
) : DatabaseManager(authManager, database) {

    private val databaseRef = database.reference
    private val invalidationSubject = PublishSubject.create<Any>()

    init {
        databaseRef.child(groupsPath()).addChildEventListener(
                FirebaseChildEventListener(invalidationSubject))
    }

    fun getItemChangeSubject(): Observable<Any> = invalidationSubject

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
