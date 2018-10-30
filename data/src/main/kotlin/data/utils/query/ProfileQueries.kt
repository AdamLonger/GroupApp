package data.utils.query

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import util.Optional
import util.toOptional
import data.firebase.ProfileManager
import data.mapper.getProfile
import data.utils.profilePath
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import model.ProfileDataModel

private fun changesOf(query: Query): Observable<DataSnapshot> = RxFirebaseDatabase.dataChanges(query)

fun ProfileManager.queryProfile(): Observable<Optional<ProfileDataModel>> {
    return changesOf(databaseRef.child(profilePath()))
            .observeOn(Schedulers.computation())
            .map { it.getProfile().toOptional() }
}
