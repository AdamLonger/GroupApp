package data.interactor

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import data.utils.FIREBASE_ERROR_INVALID_EMAIL
import data.utils.FIREBASE_ERROR_WRONG_PASSWORD
import data.utils.FIREBASE_ERROR_USER_NOT_FOUND
import interactor.LogInInteractor
import io.reactivex.Completable

class DefaultLogInInteractor(private val authManager: AuthManager,
                             private val database: FirebaseDatabase) : LogInInteractor {
    override fun logIn(email: String, pass: String): Completable {
        return authManager.logIn(email, pass)
                .flatMapCompletable { Completable.fromAction { database.goOnline() } }
                .onErrorResumeNext { error ->
                    Completable.defer {
                        when (error) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                when (error.errorCode) {
                                    FIREBASE_ERROR_WRONG_PASSWORD -> Completable.error(exception.WrongPasswordException(error))
                                    FIREBASE_ERROR_INVALID_EMAIL -> Completable.error(exception.InvalidEmailException(error))
                                    FIREBASE_ERROR_USER_NOT_FOUND -> Completable.error(exception.UserNotFoundException(error))
                                    else -> Completable.error(error)
                                }
                            }
                            else -> Completable.error(error)
                        }
                    }
                }
    }
}
