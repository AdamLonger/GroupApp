package data.firebase

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import managers.LoginManager

class DefaultLoginManager(private val authManager: AuthManager,
                          private val database: FirebaseDatabase) : LoginManager {

    override fun signIn(email: String, pass: String): Completable {
        return authManager.signIn(email, pass).flatMapCompletable { Completable.fromAction { database.goOnline() } }
    }

    override fun signOut(): Completable {
        return Completable.fromAction { database.goOffline() }.andThen(authManager.signOut())
    }

    override fun signUp(email: String, pass: String): Completable {
        return Completable.fromAction { authManager.signUp(email, pass) }
    }
}