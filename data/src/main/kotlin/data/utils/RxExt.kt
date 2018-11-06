package data.utils

import com.google.android.gms.tasks.Task
import io.reactivex.Single

fun <T> Task<T>.toSingle(): Single<T> = Single.create { emitter ->
    addOnSuccessListener { result -> emitter.takeIf { !it.isDisposed }?.onSuccess(result) }
    addOnFailureListener { result -> emitter.takeIf { !it.isDisposed }?.onError(result) }
}
