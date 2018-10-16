package interactor

import io.reactivex.Completable

interface LogInInteractor {
    fun logIn(email: String, pass: String): Completable
}
