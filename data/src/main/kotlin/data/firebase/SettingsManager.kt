package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.FirebaseDatabase
import data.utils.settingsPath
import io.reactivex.Completable
import model.SettingsObject

class SettingsManager(authManager: AuthManager, private val database: FirebaseDatabase) :
        DatabaseManager(authManager, database) {

    private val databaseRef = database.reference

    fun saveSettings(data: SettingsObject): Completable {
        return RxFirebaseDatabase.updateChildren(
                databaseRef.child(settingsPath()),
                mapOf("exampleValue" to data.exampleData))
    }
}