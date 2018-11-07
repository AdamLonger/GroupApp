package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import com.wanari.meetingtimer.common.utils.toLocalDateTime
import data.utils.listeners.FirebaseChildEventListener
import data.utils.seenPath
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.LocalDateTime

class SeenManager(authManager: AuthManager, private val database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference
    private val invalidationSubject = PublishSubject.create<Any>()

    init {
        databaseRef.child(seenPath()).addChildEventListener(
                FirebaseChildEventListener(invalidationSubject))
    }

    fun getItemChangeSubject(): Observable<Any> = invalidationSubject

    fun getItem(key: String): Single<LocalDateTime> {
        return RxFirebaseDatabase.data(databaseRef.child(seenPath(key)))
                .map {
                    (it.value as String).toLocalDateTime()
                }
    }
}