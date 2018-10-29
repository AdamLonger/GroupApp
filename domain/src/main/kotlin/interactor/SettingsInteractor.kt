package interactor

import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileObject
import model.SettingsObject

interface SettingsInteractor {
    fun logOut(): Completable
    fun saveSettings(data: SettingsObject): Completable
    fun loadProfile(): Observable<ProfileObject>
}