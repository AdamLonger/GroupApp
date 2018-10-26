package data.interactor

import data.firebase.AuthManager
import data.firebase.SettingsManager
import interactor.SettingsInteractor
import io.reactivex.Completable
import model.SettingsObject

class DefaultSettingsInteractor(private val authManager: AuthManager,
                                private val settingsManager: SettingsManager) : SettingsInteractor {

    override fun logOut(): Completable {
        return authManager.signOut()
                .doOnComplete { settingsManager.databaseGoOffline() }
    }

    override fun saveSettings(data: SettingsObject): Completable {
        return settingsManager.saveSettings(data)
    }
}
