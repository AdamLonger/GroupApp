package com.wanari.meetingtimer.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.common.utils.TRIGGER
import com.wanari.meetingtimer.navigation.NavigationOptions
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.screens.LogInScreen
import com.wanari.meetingtimer.presentation.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_settings.*
import model.SettingsObject
import org.koin.android.ext.android.inject


class SettingsScreenFragment : ScreenFragment<SettingsScreenView, SettingsViewState>(), SettingsScreenView {
    override val initialViewState = SettingsViewState()
    override val presenter by injectPresenter<SettingsPresenter>()
    override val layoutRes = R.layout.fragment_settings

    private val navigator by inject<Navigator>()
    private val logOutSubject = PublishSubject.create<Any>()
    private val saveSettingsSubject = PublishSubject.create<SettingsObject>()

    override fun getTitle(context: Context): String = "Settings Screen"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_logout_btn.setOnClickListener {
            logOutSubject.onNext(TRIGGER)
        }

        exampleButton.setOnClickListener {
            saveSettingsSubject.onNext(
                    SettingsObject(
                            exampleEditText.text.toString()
                    )
            )
        }
    }

    override fun render(viewState: SettingsViewState) {
        if (viewState.forward) {
            navigator.navigateTo(LogInScreen(),
                    NavigationOptions(purgeStack = true))
        }
    }

    override fun logOut(): Observable<Any> = logOutSubject
    override fun saveSettings(): Observable<SettingsObject> = saveSettingsSubject
}
