package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.wanari.meetingtimer.common.utils.GENERAL_PATH
import com.wanari.meetingtimer.common.utils.TRIGGER
import data.mapper.getArrayValue
import data.utils.newsPath
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import model.NewsObject

class NewsManager(authManager: AuthManager, private val database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference
    private val invalidationSubject = PublishSubject.create<Any>()
    private var subpath: String = GENERAL_PATH
    private var childEventListener: ChildEventListener

    init {
        childEventListener = object : ChildEventListener {
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

        databaseRef.child(newsPath(subpath)).addChildEventListener(childEventListener)
    }

    fun getItemChangeSubject(): PublishSubject<Any> = invalidationSubject

    fun getItems(count: Int): Single<List<NewsObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath(subpath)).orderByKey().limitToFirst(count))
                .map {
                    it.getArrayValue(NewsObject::class.java)
                }
    }

    fun getItemsAfter(key: String, count: Int): Single<List<NewsObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath(subpath)).orderByKey().startAt(key).limitToFirst(count))
                .map {
                    it.getArrayValue(NewsObject::class.java).drop(1)
                }
    }

    fun getItemsBefore(key: String, count: Int): Single<List<NewsObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath(subpath)).orderByKey().endAt(key).limitToLast(count))
                .map {
                    it.getArrayValue(NewsObject::class.java).dropLast(1)
                }
    }

    fun getItem(key: String): Single<NewsObject> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath("$subpath/$key")))
                .map {
                    it.getValue(NewsObject::class.java)
                }
    }

    fun setSubPath(path: String): Completable {
        return Completable.fromAction {
            databaseRef.child(newsPath(subpath)).removeEventListener(childEventListener)
            subpath = path
            databaseRef.child(newsPath(subpath)).addChildEventListener(childEventListener)
        }

    }
}
