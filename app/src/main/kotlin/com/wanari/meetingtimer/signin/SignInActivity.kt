package com.wanari.meetingtimer.signin

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.wanari.meetingtimer.R
import com.wanari.meetingtimer.utils.datamodels.UserAuthDM
import data.firebase.AuthManager
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.ext.android.inject

class SignInActivity : MviActivity<SignInView, SignInPresenter>() {
    override fun createPresenter() = SignInPresenter()
    private val authManager by inject<AuthManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signin_signin_btn.setOnClickListener { _ ->
            hideVirtualKeyboard()
            isValidInput().let { userAuthDM ->
                userAuthDM?.let { executeLogIn(it) }
            }
        }

        signin_password_etx.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideVirtualKeyboard()
                isValidInput().let { userAuthDM ->
                    userAuthDM?.let { executeLogIn(it) }
                }
            }
            false
        }

        signin_signup_btn.setOnClickListener {
            switchToSignUp()
        }
    }

    private fun message(text: String?, color: Int?) {
        signin_error_txv.text = text ?: ""
        color?.let {
            signin_error_txv.setTextColor(
                    ContextCompat.getColor(applicationContext!!, color)
            )
        }
    }


    private fun isValidInput(): UserAuthDM? {
        var auth: UserAuthDM? = null
        when {
            signin_email_etx?.text.toString().isEmpty() -> message(resources?.getString(R.string.message_email_empty), R.color.red)
            signin_password_etx?.text.toString().isEmpty() -> message(resources?.getString(R.string.message_pass_empty), R.color.red)
            else -> auth = UserAuthDM(signin_email_etx.text.toString(), signin_password_etx.text.toString())
        }
        return auth
    }


    private fun executeLogIn(auth: UserAuthDM) {
        lockUI(true)
//        authManager.signIn(auth.email, auth.password)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.computation())
//                .subscribe(
//                        {
//                            lockUI(false)
//                            message(null, null)
//                            switchToHome()
//                        }
//                        , { error ->
//                    Timber.e(error)
//                    lockUI(false)
//                    handleFirebaseError(error)
//                })
    }

    private fun handleFirebaseError(error: Throwable) {
        if (error.message == null) {
            message(resources?.getString(R.string.message_error), R.color.red)
        } else {
            val message = error.message.toString()
            when {
                message.contains(resources?.getString(R.string.firebase_no_user_error)!!, true) -> message(resources?.getString(R.string.message_incorrect_pass_or_user), R.color.red)
                message.contains(resources?.getString(R.string.firebase_wrong_password_error)!!, true) -> message(resources?.getString(R.string.message_incorrect_pass_or_user), R.color.red)
                message.contains(resources?.getString(R.string.firebase_email_format_error)!!, true) -> message(resources?.getString(R.string.message_email_wrong_format), R.color.red)
                else -> message(resources?.getString(R.string.message_error), R.color.red)
            }
        }
    }

    private fun lockUI(lock: Boolean) {
        signin_signin_btn.isEnabled = !lock
        signin_email_etx.isEnabled = !lock
        signin_password_etx.isEnabled = !lock

        if (lock) {
            signin_progress_bar.visibility = View.VISIBLE
        } else {
            signin_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun hideVirtualKeyboard() {
        val imm = application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    private fun switchToSignUp() {
//        val intent = Intent(this, SignupActivity::class.java)
//        startActivity(intent)
    }

    private fun switchToHome() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
    }
}