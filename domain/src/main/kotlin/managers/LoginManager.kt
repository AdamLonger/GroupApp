package managers

import io.reactivex.Completable

interface LoginManager {
    fun signIn(email: String, pass: String): Completable
    fun signOut(): Completable
    fun signUp(email: String, pass: String): Completable
}