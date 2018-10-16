package data.interactor

import com.google.firebase.database.FirebaseDatabase
import data.firebase.AuthManager
import interactor.NewsInteractor

class DefaultNewsInteractor(private val authManager: AuthManager,
                            private val database: FirebaseDatabase) : NewsInteractor {
}
