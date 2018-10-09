package com.wanari.meetingtimer.common.mvi

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

abstract class BasePresenter<V : MviView<VS>, VS>(
        private val initialState: VS
) : MviBasePresenter<V, VS>() {

    abstract fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<VS>>>

    override fun bindIntents() {
        val intentObservables = prepareIntentObservables()

        val stateObservable = Observable.merge(intentObservables)
                .scan(initialState, this::reduceState)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(stateObservable, MviView<VS>::render)
    }

    private fun reduceState(previousViewState: VS, stateChanges: ViewStateChange<VS>): VS {
        Timber.i(this.javaClass.simpleName + " -> " + stateChanges.toString())
        return stateChanges.computeNewState(previousViewState)
    }

    protected fun <T : ViewStateChange<VS>> Single<T>.asViewStateChange(): Single<ViewStateChange<VS>> = map { it as ViewStateChange<VS> }

    protected fun <T : ViewStateChange<VS>> Observable<T>.asViewStateChange(): Observable<ViewStateChange<VS>> = map { it as ViewStateChange<VS> }

    protected fun Completable.mapViewStateChange(mapper: () -> ViewStateChange<VS>): Observable<ViewStateChange<VS>> = toSingle(mapper).toObservable()

    protected fun <T> Maybe<T>.mapViewStateChange(mapper: (T) -> ViewStateChange<VS>): Observable<ViewStateChange<VS>> = map(mapper).toObservable()

    protected fun <T> Single<T>.mapViewStateChange(mapper: (T) -> ViewStateChange<VS>): Observable<ViewStateChange<VS>> = map(mapper).toObservable()

    protected fun <T> Observable<T>.mapViewStateChange(mapper: (T) -> ViewStateChange<VS>): Observable<ViewStateChange<VS>> = map(mapper)
}
