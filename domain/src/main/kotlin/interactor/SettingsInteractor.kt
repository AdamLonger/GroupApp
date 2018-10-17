package interactor

import io.reactivex.Completable

interface SettingsInteractor {
    fun logOut(): Completable
}