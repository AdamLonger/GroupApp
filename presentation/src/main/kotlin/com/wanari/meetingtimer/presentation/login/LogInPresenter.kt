package com.wanari.meetingtimer.presentation.login

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.common.ui.AppStateManager
import com.wanari.meetingtimer.presentation.R
import exception.InvalidEmailException
import exception.UserNotFoundException
import exception.WrongPasswordException
import interactor.LogInInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LogInPresenter(initialState: LogInViewState, private val logInInteractor: LogInInteractor) : BasePresenter<LogInScreenView, LogInViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<LogInViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<LogInViewState>>>()

        result.add(intent { view -> view.logIn() }
                .flatMap { (email, pass) ->
                    logInInteractor.logIn(email, pass)
                            .mapViewStateChange { LogInViewStateChanges.Forward() }
                            .onErrorReturn { error -> handleFirebaseError(error) }
                            .startWith(LogInViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(AppStateManager.getNetworkState()
                .toObservable()
                .mapViewStateChange {
                    LogInViewStateChanges.LockUI(!it)
                }
                .onErrorReturn { LogInViewStateChanges.Error(R.string.message_error) }
                .subscribeOn(Schedulers.io()))

        return result
    }

    private fun handleFirebaseError(error: Throwable): LogInViewStateChanges {
        return when (error) {
            is WrongPasswordException -> LogInViewStateChanges.Error(R.string.message_incorrect_pass_or_user)
            is UserNotFoundException -> LogInViewStateChanges.Error(R.string.message_incorrect_pass_or_user)
            is InvalidEmailException -> LogInViewStateChanges.Error(R.string.message_email_wrong_format)
            else -> LogInViewStateChanges.Error(R.string.message_error)
        }
    }
}
