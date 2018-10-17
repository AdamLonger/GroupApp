package com.wanari.meetingtimer.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import com.wanari.meetingtimer.R
import com.wanari.meetingtimer.common.di.createScreen
import com.wanari.meetingtimer.common.rx.Schedulers
import com.wanari.meetingtimer.common.ui.BaseActivity
import com.wanari.meetingtimer.common.ui.ForegroundManager
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.common.utils.setVisiblity
import com.wanari.meetingtimer.navigation.*
import com.wanari.meetingtimer.navigation.screens.LogInScreen
import com.wanari.meetingtimer.navigation.screens.NewsScreen
import com.wanari.meetingtimer.navigation.screens.SettingsScreen
import com.wanari.meetingtimer.presentation.login.LogInScreenFragment
import com.wanari.meetingtimer.presentation.news.NewsScreenFragment
import com.wanari.meetingtimer.presentation.settings.SettingsScreenFragment
import com.wanari.meetingtimer.presentation.signup.SignUpScreenFragment
import data.firebase.AuthManager
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_root.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.ref.WeakReference


class RootActivity : BaseActivity() {
    private val screenManager by inject<ScreenManager>()
    private val schedulers by inject<Schedulers>()
    private val navigator by inject<Navigator>()
    private val authManager by inject<AuthManager>()

    private var currentScreenFragment: WeakReference<out ScreenFragment<*, *>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        //handleIntent(intent)

        screenManager.dispatchNavigationEvent()
                .subscribeOn(schedulers.computation())
                .observeOn(schedulers.ui())
                .subscribe(this::navigate)
                .disposeOnDestroy()

        authManager.isAuthenticated()
                .subscribeOn(schedulers.computation())
                .observeOn(schedulers.computation())
                .subscribe {
                    if (!it && (currentScreenFragment?.get() !is LogInScreenFragment ||
                                    currentScreenFragment?.get() == null)) {
                        navigator.navigateTo(LogInScreen(),
                                NavigationOptions(purgeStack = true))
                    }
                }.disposeOnDestroy()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        rootNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.root_navigation_item_news -> {
                    if (currentScreenFragment?.get() !is NewsScreenFragment)
                        navigator.navigateTo(NewsScreen(),
                                NavigationOptions(purgeStack = true))
                }
                R.id.root_navigation_item_user -> {
                    //Navigate
                }
                R.id.root_navigation_item_groups -> {
                    //Navigate
                }
                R.id.root_navigation_item_settings -> {
                    if (currentScreenFragment?.get() !is SettingsScreenFragment)
                        navigator.navigateTo(SettingsScreen(),
                                NavigationOptions(purgeStack = true))
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        if (currentScreenFragment?.get()?.onBackPressed() == true) {
            return
        }

        if (screenManager.goBack()) {
            return
        }

        super.onBackPressed()
    }

    private fun navigate(event: NavigationEvent) {
        val fragment = createScreen(event.screen)

        supportFragmentManager.apply {
            val currentFragment = findFragmentByTag(FRAGMENT_TAG)

            event.options.sharedElements?.let { sharedHolder ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragment.setSharedElementEnterTransition(TransitionInflater
                            .from(this@RootActivity)
                            .inflateTransition(sharedHolder.transitionRes))
                }
            }

            beginTransaction().apply {
                disallowAddToBackStack()
                event.options.applyTo(this)
                currentFragment?.let { remove(it) }
                add(R.id.rootContainer, fragment, FRAGMENT_TAG)
                setReorderingAllowed(true)
            }.commitNowAllowingStateLoss()

            rootNavigationView.setVisiblity(fragment !is LogInScreenFragment &&
                    fragment !is SignUpScreenFragment)
        }

        currentScreenFragment = WeakReference(fragment)

        Timber.d("Navigate to: ${event.screen}")
    }

    private fun handleIntent(intent: Intent) {
        if (!intent.hasExtra(EXTRA_SCREEN_KEY)) {
            navigator.navigateToHome()
            return
        }

        val screen = intent.getParcelableExtra<Screen>(EXTRA_SCREEN_KEY)
        intent.removeExtra(EXTRA_SCREEN_KEY)

        if (screenManager.defaultScreen == screen) {
            navigator.navigateToHome()
        } else {
            navigator.navigateTo(screen)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    companion object {
        private const val FRAGMENT_TAG = "SCREEN_FRAGMENT_TAG"
        private const val EXTRA_SCREEN_KEY = "EXTRA_SCREEN_KEY"
    }

    override fun onResume() {
        super.onResume()
        ForegroundManager.setState(true)
    }

    override fun onPause() {
        super.onPause()
        ForegroundManager.setState(false)
    }
}
