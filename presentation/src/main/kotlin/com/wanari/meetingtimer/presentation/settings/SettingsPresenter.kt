package com.wanari.meetingtimer.presentation.settings

import com.wanari.meetingtimer.common.mvi.BasePresenter
import com.wanari.meetingtimer.common.mvi.ViewStateChange
import com.wanari.meetingtimer.common.ui.AppStateManager
import com.wanari.meetingtimer.presentation.R
import interactor.SettingsInteractor
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers
import model.ProfileObject
import util.Some

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

        result.add(settingsInteractor.loadProfile().ofType<Some<ProfileObject>>()
                .mapViewStateChange { SettingsViewStateChanges.ProfileLoaded(it.value) }
                .onErrorReturn { _ -> SettingsViewStateChanges.Error(R.string.message_error) }
                .startWith(SettingsViewStateChanges.Loading())
                .subscribeOn(Schedulers.io()))

        result.add(intent { view -> view.saveProfile() }
                .flatMap { data ->
                    settingsInteractor.saveProfile(data)
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
                    SettingsViewStateChanges.LockUI(!it)
                }
                .onErrorReturn { SettingsViewStateChanges.Error(R.string.message_error) }
                .subscribeOn(Schedulers.io()))

        return result
    }
}
