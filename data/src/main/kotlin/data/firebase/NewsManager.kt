package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import com.longer.groupapp.common.utils.GENERAL_PATH
import data.mapper.getArrayValue
import data.utils.listeners.FirebaseChildEventListener
import data.utils.newsPath
import io.reactivex.Observable
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
        childEventListener = FirebaseChildEventListener(invalidationSubject)
        databaseRef.child(newsPath(subpath)).addChildEventListener(childEventListener)
    }

    fun getItemChangeSubject(): Observable<Any> = invalidationSubject

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

    fun setSubPath(path: String): Single<Boolean> {

        databaseRef.child(newsPath(subpath)).removeEventListener(childEventListener)
        subpath = path
        databaseRef.child(newsPath(subpath)).addChildEventListener(childEventListener)

        return RxFirebaseDatabase.data(databaseRef.child(newsPath(subpath)))
                .map {
                    it.hasChildren()
                }
    }

}
