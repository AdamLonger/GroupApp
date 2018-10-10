package com.wanari.meetingtimer.signin

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import io.reactivex.Observable

class SignInPresenter : BasePresenter<SignInView, String>() {
    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<String>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}