package data.interactor

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import data.utils.FIREBASE_ERROR_EMAIL_ALREADY_IN_USE
import data.utils.FIREBASE_ERROR_INVALID_EMAIL
import data.utils.FIREBASE_ERROR_WEAK_PASSWORD
import interactor.SignUpInteractor
import io.reactivex.Completable

class DefaultSignUpInteractor(private val authManager: AuthManager,
                              private val database: FirebaseDatabase) : SignUpInteractor {

    override fun signUp(email: String, pass: String): Completable {
        return authManager.signUp(email, pass)
                .flatMapCompletable { Completable.fromAction { database.goOnline() } }
                .onErrorResumeNext { error ->
                    Completable.defer {
                        when (error) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                when (error.errorCode) {
                                    FIREBASE_ERROR_INVALID_EMAIL -> Completable.error(exception.InvalidEmailException(error))
                                    FIREBASE_ERROR_WEAK_PASSWORD -> Completable.error(exception.WeakPasswordException(error))
                                    else -> Completable.error(error)
                                }
                            }
                            is FirebaseAuthUserCollisionException -> {
                                when (error.errorCode) {
                                    FIREBASE_ERROR_EMAIL_ALREADY_IN_USE -> Completable.error(exception.EmailAlreadyInUseException(error))
                                    else -> Completable.error(error)
                                }
                            }
                            else -> Completable.error(error)
                        }
                    }
                }
    }
}
