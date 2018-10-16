package com.wanari.meetingtimer.presentation.signup

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.presentation.R
import exception.EmailAlreadyInUseException
import exception.InvalidEmailException
import exception.WeakPasswordException
import interactor.SignUpInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SignUpPresenter(initialState: SignUpViewState, private val signUpInteractor: SignUpInteractor) : BasePresenter<SignUpScreenView, SignUpViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<SignUpViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<SignUpViewState>>>()

        result.add(intent { view -> view.signUp() }
                .flatMap { (email, pass) ->
                    signUpInteractor.signUp(email, pass)
                            .mapViewStateChange { SignUpViewStateChanges.Forward() }
                            .onErrorReturn { error -> handleFirebaseError(error) }
                            .startWith(SignUpViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        return result
    }

    private fun handleFirebaseError(error: Throwable): SignUpViewStateChanges {
        return when (error) {
            is WeakPasswordException -> SignUpViewStateChanges.Error(R.string.message_weak_password)
            is EmailAlreadyInUseException -> SignUpViewStateChanges.Error(R.string.message_email_already_exists)
            is InvalidEmailException -> SignUpViewStateChanges.Error(R.string.message_email_wrong_format)
            else -> SignUpViewStateChanges.Error(R.string.message_error)
        }
    }
}
