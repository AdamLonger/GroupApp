package data.utils.listeners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.wanari.meetingtimer.common.utils.TRIGGER
import io.reactivex.subjects.PublishSubject

class FirebaseChildEventListener(private val invalidationSubject: PublishSubject<Any>) : ChildEventListener {
    override fun onCancelled(p0: DatabaseError) {}

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        invalidationSubject.onNext(TRIGGER)
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        invalidationSubject.onNext(TRIGGER)
    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        invalidationSubject.onNext(TRIGGER)
    }

    override fun onChildRemoved(p0: DataSnapshot) {
        invalidationSubject.onNext(TRIGGER)
    }
}