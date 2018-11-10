package com.longer.groupapp.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.longer.groupapp.common.ui.AppStateManager
import com.longer.groupapp.common.ui.ScreenFragment
import com.longer.groupapp.common.utils.clear
import com.longer.groupapp.common.utils.isEmptyOrNull
import com.longer.groupapp.common.utils.lock
import com.longer.groupapp.navigation.NavigationOptions
import com.longer.groupapp.navigation.Navigator
import com.longer.groupapp.navigation.screens.NewsScreen
import com.longer.groupapp.navigation.screens.SignUpScreen
import com.longer.groupapp.presentation.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_log_in.*
import org.koin.android.ext.android.inject

class LogInScreenFragment : ScreenFragment<LogInScreenView, LogInViewState>(), LogInScreenView {
    override val initialViewState = LogInViewState()
    override val presenter by injectPresenter<LogInPresenter>()
    override val hasToolbar = false
    override val layoutRes = R.layout.fragment_log_in

    private val navigator by inject<Navigator>()
    private val logInSubject = PublishSubject.create<Pair<String, String>>()

    override fun getTitle(context: Context): String = "Log In Screen"

    override fun logIn(): Observable<Pair<String, String>> = logInSubject

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_login_btn.setOnClickListener {
            hideVirtualKeyboard()
            isValidInput().let { pair ->
                if (pair != null) logInSubject.onNext(pair)
            }
        }

        login_password_etx.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideVirtualKeyboard()
                isValidInput()?.let {
                    logInSubject.onNext(it)
                }
            }
            false
        }

        login_signup_btn.setOnClickListener {
            navigator.navigateTo(SignUpScreen(), NavigationOptions(purgeStack = true))
        }
    }

    override fun render(viewState: LogInViewState) {
        viewState.uiLocked.let {
            login_email_etx.lock(it)
            login_password_etx.lock(it)
            login_login_btn.isClickable = !it
        }


        handleLoading(viewState.loading)

        login_error_txv.setText(viewState.errorRes ?: R.string.emptyString)
        if (viewState.forward) {
            navigator.navigateTo(NewsScreen(), NavigationOptions(purgeStack = true))
        }
    }

    private fun handleLoading(lock: Boolean) {
        login_error_txv.clear()
        AppStateManager.setLoadingState(lock)
    }

    private fun hideVirtualKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun isValidInput(): Pair<String, String>? {
        val message = when {
            login_email_etx.isEmptyOrNull() ->
                resources.getString(R.string.message_email_empty)
            login_password_etx.isEmptyOrNull() ->
                resources.getString(R.string.message_pass_empty)
            else -> null
        }

        return if (message == null) {
            Pair(login_email_etx.text.toString(), login_password_etx.text.toString())
        } else {
            login_error_txv.text = message
            null
        }
    }
}
