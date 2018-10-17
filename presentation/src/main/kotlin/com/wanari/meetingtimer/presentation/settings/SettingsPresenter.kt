package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.presentation.R
import interactor.SettingsInteractor
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SettingsPresenter(initialState: SettingsViewState, private val settingsInteractor: SettingsInteractor) : BasePresenter<SettingsScreenView, SettingsViewState>(initialState) {

    override fun prepareIntentObservables(): ArrayList<Observable<ViewStateChange<SettingsViewState>>> {
        val result = ArrayList<Observable<ViewStateChange<SettingsViewState>>>()


        result.add(intent { view -> view.logOut() }
                .flatMap { _ ->
                    settingsInteractor.logOut()
                            .mapViewStateChange { SettingsViewStateChanges.Initial() }
                            .onErrorReturn { SettingsViewStateChanges.Error(R.string.message_error) }
                            .subscribeOn(Schedulers.io())
                })

        return result
    }

}
