package interactor

import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileDataModel
import model.SettingsObject
import util.Optional

interface SettingsInteractor {
    fun logOut(): Completable

    fun loadProfile(): Observable<Optional<ProfileDataModel>>
    fun saveProfile(data: ProfileDataModel): Completable

    fun saveSettings(data: SettingsObject): Completable
}