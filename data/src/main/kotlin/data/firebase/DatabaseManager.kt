package data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

abstract class DatabaseManager(
        val firebaseAuth: FirebaseAuth,
        val firebaseDatabase: FirebaseDatabase) {

    fun databaseGoOffline() {
        firebaseDatabase.goOffline()
    }
}
