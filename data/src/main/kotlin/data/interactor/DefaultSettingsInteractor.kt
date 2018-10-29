package data.interactor

import data.firebase.AuthManager
import data.firebase.ProfileManager
import data.firebase.SettingsManager
import interactor.SettingsInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileObject
import model.SettingsObject

class DefaultSettingsInteractor(private val authManager: AuthManager,
                                private val profilManager: ProfileManager,
                                private val settingsManager: SettingsManager) : SettingsInteractor {

    override fun logOut(): Completable {
        return authManager.signOut()
                .doOnComplete { settingsManager.databaseGoOffline() }
    }

    override fun saveSettings(data: SettingsObject): Completable {
        return settingsManager.saveSettings(data)
    }

    override fun loadProfile(): Observable<ProfileObject> {
        return profilManager.getProfile()
                .map {
                    if (it.isNotEmpty()) {
                        it.last().toWeight()?.let {
                            return@map Optional(it)
                        }
                    }
                    return@map Optional(null)
                }
    }
}
