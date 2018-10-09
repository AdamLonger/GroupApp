package data.interactor

import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import io.reactivex.Completable
import interactor.SignUpInteractor

class DefaultSignUpInteractor(private val authManager: AuthManager,
                              private val database: FirebaseDatabase) : SignUpInteractor {

    override fun signUp(email: String, pass: String): Completable {
        return authManager.signUp(email, pass)
                .ignoreElement()
    }
}