package data.firebase


import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import data.utils.messagingTokensPath
import io.reactivex.Completable

class DeviceInfoManager(
        authManager: AuthManager,
        database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference

    fun updateMessagingToken(token: String): Completable {
        FirebaseInstanceId.getInstance().instanceId
        ASd
        return authManager.isAuthenticated().firstElement().flatMapCompletable {
            if (it) {
                return@flatMapCompletable RxFirebaseDatabase.setValue(databaseRef.child(messagingTokensPath()), token)
            }
            return@flatMapCompletable Completable.complete()
        }
    }

}