package com.longer.groupapp.presentation.signup

import com.longer.groupapp.common.mvi.BasePresenter
import com.longer.groupapp.common.mvi.ViewStateChange
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.presentation.R
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

        result.add(AppStateManager.getNetworkState()
                .toObservable()
                .mapViewStateChange {
                    SignUpViewStateChanges.LockUI(!it) }
                .onErrorReturn { SignUpViewStateChanges.Error(R.string.message_error) }
                .subscribeOn(Schedulers.io()))

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
