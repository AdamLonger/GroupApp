package com.wanari.meetingtimer.presentation.signup

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.common.utils.clear
import com.wanari.meetingtimer.common.utils.isEmptyOrNull
import com.wanari.meetingtimer.common.utils.setVisiblity
import com.wanari.meetingtimer.navigation.NavigationOptions
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.screens.LogInScreen
import com.wanari.meetingtimer.presentation.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

class SignUpScreenFragment : ScreenFragment<SignUpScreenView, SignUpViewState>(), SignUpScreenView {
    override val initialViewState = SignUpViewState()
    override val presenter by injectPresenter<SignUpPresenter>()
    override val layoutRes = R.layout.fragment_sign_up

    private val navigator by inject<Navigator>()
    private val signUpSubject = PublishSubject.create<Pair<String, String>>()

    override fun getTitle(context: Context): String = "Sign Up Screen"

    override fun signUp(): Observable<Pair<String, String>> = signUpSubject

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signup_signup_btn.setOnClickListener {
            hideVirtualKeyboard()
            isValidInput()?.let { pair ->
                signUpSubject.onNext(pair)
            }
        }

        signup_confirm_password_etx.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideVirtualKeyboard()
                isValidInput()?.let { pair ->
                    signUpSubject.onNext(pair)
                }
            }
            false
        }

        signup_login_btn.setOnClickListener {
            navigator.navigateBackTo(LogInScreen(), NavigationOptions(purgeStack = true))
        }
    }

    override fun render(viewState: SignUpViewState) {
        lockUI(viewState.loading)
        signup_error_txv.setText(viewState.errorRes ?: R.string.emptyString)
        if (viewState.forward) {
            navigator.navigateBackTo(LogInScreen(), NavigationOptions(purgeStack = true))
            Toast.makeText(
                    context,
                    resources.getString(R.string.toast_user_created),
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun lockUI(lock: Boolean) {
        signup_input_group.isEnabled = !lock
        signup_error_txv.clear()
        signup_progress_group.setVisiblity(lock)
        if (lock) signup_progress_group.bringToFront()
    }

    private fun hideVirtualKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun isValidInput(): Pair<String, String>? {
        val message = when {
            signup_email_etx.isEmptyOrNull() ->
                resources.getString(R.string.message_email_empty)
            signup_password_etx.isEmptyOrNull() ->
                resources.getString(R.string.message_pass_empty)
            signup_confirm_password_etx.isEmptyOrNull() ->
                resources.getString(R.string.message_conf_empty)
            signup_password_etx.text.toString()
                    != signup_confirm_password_etx.text.toString() ->
                resources.getString(R.string.message_confirmation_match_error)
            else -> null
        }

        return if (message == null) {
            Pair(signup_email_etx.text.toString(), signup_password_etx.text.toString())
        } else {
            signup_error_txv.text = message
            null
        }
    }
}
