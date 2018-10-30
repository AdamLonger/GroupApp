package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.common.ui.AppStateManager
import com.wanari.meetingtimer.presentation.R
import com.wanari.meetingtimer.presentation.model.ProfileObject
import com.wanari.meetingtimer.presentation.model.parse
import com.wanari.meetingtimer.presentation.model.toDataModel
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

        result.add(settingsInteractor.loadProfile()
                .map { ProfileObject().parse(it) }
                .mapViewStateChange { SettingsViewStateChanges.ProfileLoaded(it) }
                .onErrorReturn { error -> SettingsViewStateChanges.Error(R.string.message_error) }
                .startWith(SettingsViewStateChanges.Loading())
                .subscribeOn(Schedulers.io()))

        result.add(intent { view -> view.saveProfile() }
                .flatMap { data ->
                    settingsInteractor.saveProfile(data.toDataModel())
                            .mapViewStateChange { SettingsViewStateChanges.Initial() }
                            .onErrorReturn { SettingsViewStateChanges.Error(R.string.message_error) }
                            .startWith(SettingsViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(intent { view -> view.saveSettings() }
                .flatMap { data ->
                    settingsInteractor.saveSettings(data)
                            .mapViewStateChange { SettingsViewStateChanges.Initial() }
                            .onErrorReturn { SettingsViewStateChanges.Error(R.string.message_error) }
                            .startWith(SettingsViewStateChanges.Loading())
                            .subscribeOn(Schedulers.io())
                })

        result.add(AppStateManager.getNetworkState()
                .toObservable()
                .mapViewStateChange {
                    SettingsViewStateChanges.LockUI(!it) }
                .onErrorReturn { SettingsViewStateChanges.Error(R.string.message_error) }
                .subscribeOn(Schedulers.io()))

        return result
    }
}
