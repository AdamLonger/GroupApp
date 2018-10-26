package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import data.mapper.getArrayValue
import data.utils.newsPath
import io.reactivex.Single
import model.NewsObject

class NewsManager(private val auth: FirebaseAuth, private val database: FirebaseDatabase) :
        DatabaseManager(auth, database) {

    private val databaseRef = database.reference

    fun getAnimals(count: Int): Single<List<NewsObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath()).orderByKey().limitToFirst(count))
                .map {
                    it.getArrayValue(NewsObject::class.java)
                }
    }

    fun getAnimalsAfter(key: String, count: Int): Single<List<NewsObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath()).orderByKey().startAt(key).limitToFirst(count))
                .map {
                    it.getArrayValue(NewsObject::class.java).drop(1)
                }
    }

    fun getAnimalsBefore(key: String, count: Int): Single<List<NewsObject>> {
        return RxFirebaseDatabase.data(databaseRef.child(newsPath()).orderByKey().endAt(key).limitToLast(count))
                .map {
                    it.getArrayValue(NewsObject::class.java).dropLast(1)
                }
    }
}