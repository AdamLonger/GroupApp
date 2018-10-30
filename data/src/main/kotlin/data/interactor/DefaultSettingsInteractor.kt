package data.interactor

import data.firebase.AuthManager
import data.firebase.ProfileManager
import data.firebase.SettingsManager
import interactor.SettingsInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileDataModel
import model.SettingsObject
import util.Optional

class DefaultSettingsInteractor(private val authManager: AuthManager,
                                private val profileManager: ProfileManager,
                                private val settingsManager: SettingsManager) : SettingsInteractor {

    override fun logOut(): Completable {
        return authManager.signOut()
                .doOnComplete { settingsManager.databaseGoOffline() }
    }

    override fun loadProfile(): Observable<Optional<ProfileDataModel>> {
        return profileManager.loadProfile()
    }

    override fun saveProfile(data: ProfileDataModel): Completable {
        return profileManager.saveProfile(data)
    }

    override fun saveSettings(data: SettingsObject): Completable {
        return settingsManager.saveSettings(data)
    }
}
