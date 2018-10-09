package com.wanari.meetingtimer.presentation.signin

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import io.reactivex.Observable
import interactor.SignInInteractor

class StartPresenter(initialState: String, private val signInInteractor: SignInInteractor) : BasePresenter<StartScreenView, String>(initialState) {
    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<String>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}