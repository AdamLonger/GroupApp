package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import data.mapper.toMap
import data.utils.profilePath
import data.utils.query.queryHolder
import data.utils.query.queryProfile
import data.utils.settingsPath
import io.reactivex.Completable
import io.reactivex.Observable
import model.ProfileDataModel
import model.SettingsObject
import util.Optional

class ProfileManager(authManager: AuthManager, private val database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    internal val databaseRef = database.reference

    private val profileQueryHolder by queryHolder(authManager.isAuthenticated()) { queryProfile() }

    fun loadProfile(): Observable<Optional<ProfileDataModel>> {
        return profileQueryHolder.query()
    }

    fun saveProfile(data: ProfileDataModel): Completable {
        return RxFirebaseDatabase.updateChildren(
                databaseRef.child(profilePath()),
                data.toMap())
    }

    fun saveSettings(data: SettingsObject): Completable {
        return RxFirebaseDatabase.updateChildren(
                databaseRef.child(settingsPath()),
                mapOf("exampleValue" to data.exampleData))
    }
}