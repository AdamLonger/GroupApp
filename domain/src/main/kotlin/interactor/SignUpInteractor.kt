package interactor

import io.reactivex.Completable

interface SignUpInteractor {
    fun signUp(email: String, pass: String): Completable
}