package data.interactor

import data.firebase.AuthManager
import data.firebase.ProfileManager
import data.firebase.SettingsManager
import data.mapper.toDataModel
import data.mapper.toObject
import interactor.SettingsInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileObject
import model.SettingsObject
import util.Optional
import util.toOptional

class DefaultSettingsInteractor(private val authManager: AuthManager,
                                private val profileManager: ProfileManager,
                                private val settingsManager: SettingsManager) : SettingsInteractor {

    override fun logOut(): Completable {
        return authManager.signOut()
                .doOnComplete { settingsManager.databaseGoOffline() }
    }

    override fun loadProfile(): Observable<Optional<ProfileObject>> {
        return profileManager.loadProfile().map {
            it.component1()?.toObject().toOptional() }
    }

    override fun saveProfile(data: ProfileObject): Completable {
        return profileManager.saveProfile(data.toDataModel())
    }

    override fun saveSettings(data: SettingsObject): Completable {
        return settingsManager.saveSettings(data)
    }
}
