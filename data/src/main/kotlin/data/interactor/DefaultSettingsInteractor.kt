package data.interactor

import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import interactor.SettingsInteractor
import io.reactivex.Completable

class DefaultSettingsInteractor(private val authManager: AuthManager,
                                private val database: FirebaseDatabase) : SettingsInteractor {
    override fun logOut(): Completable {
        return authManager.signOut()
                .doOnComplete { database.goOffline() }
    }
}
