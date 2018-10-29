package data.firebase

import com.google.firebase.database.FirebaseDatabase
import com.wanari.meetingtimer.common.utils.Optional
import data.utils.Query.queryHolder
import data.utils.Query.queryProfile
import io.reactivex.Observable
import model.ProfileObject
import model.SettingsObject

class ProfileManager(authManager: AuthManager, private val database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    internal val databaseRef = database.reference

    private val profileQueryHolder by queryHolder(authManager.isAuthenticated()) { queryProfile() }

    fun getProfile(data: SettingsObject): Observable<Optional<ProfileObject>> {
        return profileQueryHolder.query()
    }

}