package interactor

import io.reactivex.Completable

interface SignInInteractor {
    fun signIn(email: String, pass: String): Completable
    fun signOut(): Completable
}