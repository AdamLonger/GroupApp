package data.firebase

import com.androidhuman.rxfirebase2.auth.rxSignOut
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jakewharton.rx.replayingShare
import com.wanari.meetingtimer.common.utils.None
import com.wanari.meetingtimer.common.utils.Optional
import com.wanari.meetingtimer.common.utils.Some
import com.wanari.meetingtimer.common.utils.toOptional
import data.utils.toSingle
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType

class AuthManager(private val firebaseAuth: FirebaseAuth) {

    private val authChanged: Observable<Optional<FirebaseUser>> by lazy {
        Observable.create<Optional<FirebaseUser>> { emitter ->
            val listener = FirebaseAuth.AuthStateListener { p0 ->
                emitter.takeIf { !it.isDisposed }?.onNext(p0.currentUser.toOptional())
            }
            firebaseAuth.addAuthStateListener(listener)
            emitter.setCancellable { firebaseAuth.removeAuthStateListener(listener) }
        }.startWith(None).distinctUntilChanged().replayingShare()
    }

    fun isAuthenticated(): Observable<Boolean> {
        return authChanged.map { it is Some }
    }

    fun getCurrentUser(): Observable<FirebaseUser> {
        return authChanged
                .ofType<Some<FirebaseUser>>()
                .map { it.value }
    }

    fun logIn(email: String, pass: String): Single<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, pass).toSingle()
    }

    fun signOut(): Completable {
        return firebaseAuth.rxSignOut()
    }

    fun signUp(email: String, pass: String): Single<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, pass).toSingle()
    }
}