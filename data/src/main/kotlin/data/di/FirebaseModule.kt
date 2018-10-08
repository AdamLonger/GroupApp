package data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import data.firebase.AuthManager
import org.koin.dsl.module.module

const val MAX_CACHE_SIZE = 1024L * 1024L * 100L

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance() }
    single {
        FirebaseDatabase.getInstance().apply {
            setPersistenceCacheSizeBytes(MAX_CACHE_SIZE)
            setPersistenceEnabled(true)
        }
    }
    single { AuthManager(get()) }
}