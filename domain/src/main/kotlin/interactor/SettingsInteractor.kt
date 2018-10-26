package interactor

import io.reactivex.Completable
import model.SettingsObject

interface SettingsInteractor {
    fun logOut(): Completable
    fun saveSettings(data: SettingsObject): Completable
}