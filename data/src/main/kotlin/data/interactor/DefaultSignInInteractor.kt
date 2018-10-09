package data.interactor

import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import io.reactivex.Completable
import interactor.SignInInteractor

class DefaultSignInInteractor(private val authManager: AuthManager,
                              private val database: FirebaseDatabase) : SignInInteractor {
    override fun signIn(email: String, pass: String): Completable {
        return authManager.signIn(email, pass)
                .flatMapCompletable { Completable.fromAction { database.goOnline() } }
    }

    override fun signOut(): Completable {
        return Completable.fromAction { database.goOffline() }
                .andThen(authManager.signOut())
    }
}