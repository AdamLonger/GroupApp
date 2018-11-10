package data.firebase


import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import data.utils.messagingTokensPath
import data.utils.toSingle
import io.reactivex.Completable

class DeviceInfoManager(
        authManager: AuthManager,
        database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference

    //Fires immediately
    fun updateMessagingToken(): Completable {
        return FirebaseInstanceId.getInstance().instanceId.toSingle()
                .flatMapCompletable {instanceId ->
                    return@flatMapCompletable RxFirebaseDatabase.setValue(databaseRef.child(messagingTokensPath()), instanceId.token)
                }.onErrorComplete()
    }


    //Fires only if authenticated
    fun updateMessagingToken(token: String): Completable {
        return authManager.isAuthenticated().firstElement().flatMapCompletable {
            if (it) {
                return@flatMapCompletable RxFirebaseDatabase.setValue(databaseRef.child(messagingTokensPath()), token)
            }
            return@flatMapCompletable Completable.complete()
        }
    }

}