package data.firebase

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import data.utils.settingsPath
import data.utils.toMap
import io.reactivex.Completable
import model.SettingsObject

class SettingsManager(private val auth: FirebaseAuth, private val database: FirebaseDatabase) :
        DatabaseManager(auth, database) {

    private val databaseRef = database.reference

    fun saveSettings(data: SettingsObject): Completable {
        return RxFirebaseDatabase.updateChildren(databaseRef.child(settingsPath()), data.toMap())
    }
}