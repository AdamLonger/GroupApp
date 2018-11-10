package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import com.longer.groupapp.common.utils.toFirebaseString
import com.longer.groupapp.common.utils.toLocalDateTime
import data.utils.consts.DEFAULT_FIREBASE_DATE_TIME
import data.utils.listeners.FirebaseChildEventListener
import data.utils.seenPath
import io.reactivex.Completable
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

    fun removeKey(key: String): Completable {
        val userid = authManager.getCurrentUserBlocking()?.uid
        userid?.let { uid ->
            return RxFirebaseDatabase.removeValue(
                    databaseRef.child(seenPath("/$key")))
        }
        return Completable.complete()
    }

    fun putValue(key: String): Completable {
        val userid = authManager.getCurrentUserBlocking()?.uid
        userid?.let { uid ->
            return RxFirebaseDatabase.setValue(
                    databaseRef.child(seenPath("/$key")),
                    LocalDateTime.now().toFirebaseString() ?: DEFAULT_FIREBASE_DATE_TIME)

        }
        return Completable.complete()
    }
}
