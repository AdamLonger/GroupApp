package com.longer.groupapp.presentation.settings

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.common.ui.ScreenFragment
import com.longer.groupapp.common.utils.TRIGGER
import com.longer.groupapp.common.utils.toDisplayLocalDate
import com.longer.groupapp.common.utils.toDisplayString
import com.longer.groupapp.navigation.NavigationOptions
import com.longer.groupapp.navigation.Navigator
import com.longer.groupapp.navigation.screens.LogInScreen
import com.longer.groupapp.presentation.R
import model.ProfileObject
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_settings.*
import model.SettingsObject
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDate
import android.widget.NumberPicker
import android.support.v7.app.AlertDialog
import com.longer.groupapp.presentation.utils.FEMALE_TEXT
import com.longer.groupapp.presentation.utils.MALE_TEXT
import com.longer.groupapp.presentation.utils.getName
import com.longer.groupapp.presentation.utils.getStringRes
import enums.GenderEnum


class SettingsScreenFragment : ScreenFragment<SettingsScreenView, SettingsViewState>(), SettingsScreenView {
    override val initialViewState = SettingsViewState()
    override val presenter by injectPresenter<SettingsPresenter>()
    override val hasToolbar = true
    override val layoutRes = R.layout.fragment_settings

    private val navigator by inject<Navigator>()
    private val logOutSubject = PublishSubject.create<Any>()
    private val saveSettingsSubject = PublishSubject.create<SettingsObject>()
    private val saveProfileSubject = PublishSubject.create<ProfileObject>()

    override fun getTitle(context: Context): String = "Settings Screen"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_logout_btn.setOnClickListener {
            logOutSubject.onNext(TRIGGER)
        }

        settings_save_btn.setOnClickListener {
            var birth: LocalDate? = null
            if (settings_birth_etx.text.toString().isNotEmpty()) {
                birth = settings_birth_etx.text.toString().toDisplayLocalDate()
            }

            var gender: GenderEnum? = null
            if (GenderEnum.values().map { enum -> enum.name }
                            .contains(settings_gender_etx.text.toString())
            ) gender = GenderEnum.valueOf(settings_gender_etx.text.toString())

            saveProfileSubject.onNext(
                    ProfileObject(
                            name = settings_name_etx.text.toString(),
                            birth = birth,
                            gender = gender)
            )
        }

        settings_birth_etx.setOnClickListener {
            settings_birth_etx.isClickable = false
            DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        settings_birth_etx.setText(LocalDate.of(year, month+1, dayOfMonth)
                                .toDisplayString())
                        settings_birth_etx.isClickable = true
                    },
                    2000,
                    0,
                    1).show()
        }

        settings_gender_etx.setOnClickListener {
            context?.let {context -> handleGenderPickIntent(context) }
        }
    }

    override fun render(viewState: SettingsViewState) {
        AppStateManager.setLoadingState(viewState.loading)

        if (viewState.forward) {
            navigator.navigateTo(LogInScreen(),
                    NavigationOptions(purgeStack = true))
        }

        viewState.uiLocked.let {
            settings_save_btn.isEnabled = !it
            settings_logout_btn.isEnabled = !it
        }


        viewState.profile?.let { profileObject ->
            settings_name_etx.setText(profileObject.name ?: "")
            settings_birth_etx.setText(profileObject.birth?.toDisplayString() ?: "")
            settings_gender_etx.setText(profileObject.gender?.getName() ?: "")

        }
    }

    private fun handleGenderPickIntent(context:Context){
        settings_gender_etx.isClickable = false

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Select Gender")
        val picker = NumberPicker(context)
        picker.minValue = 0
        picker.maxValue = 1
        picker.displayedValues = arrayOf(MALE_TEXT, FEMALE_TEXT)

        alertDialogBuilder.setView(picker)
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok"
                ) { dialog, _ ->

                    settings_gender_etx?.setText(
                            GenderEnum.valueOf(
                                    picker.displayedValues[picker.value]
                            ).getStringRes()
                    )

                    settings_gender_etx.isClickable = true
                    dialog.cancel()
                }
                .setNegativeButton("Cancel"
                ) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun logOut(): Observable<Any> = logOutSubject
    override fun saveSettings(): Observable<SettingsObject> = saveSettingsSubject
    override fun saveProfile(): Observable<ProfileObject> = saveProfileSubject
}
