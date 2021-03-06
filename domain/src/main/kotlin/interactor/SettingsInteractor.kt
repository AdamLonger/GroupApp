package interactor

import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileDataModel
import model.ProfileObject
import model.SettingsObject
import util.Optional

interface SettingsInteractor {
    fun logOut(): Completable

    fun loadProfile(): Observable<Optional<ProfileObject>>
    fun saveProfile(data: ProfileObject): Completable

    fun saveSettings(data: SettingsObject): Completable
}