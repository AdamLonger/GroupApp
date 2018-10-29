package data.utils.Query

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.wanari.meetingtimer.common.utils.Optional
import data.firebase.ProfileManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import model.ProfileObject

private fun changesOf(query: Query): Observable<DataSnapshot> = RxFirebaseDatabase.dataChanges(query)

fun ProfileManager.queryProfile(): Observable<Optional<ProfileObject>> {
    return changesOf(databaseRef.child(profilePath()))
            .observeOn(Schedulers.computation())
            .map { Optional(it.getProfile()) }
}