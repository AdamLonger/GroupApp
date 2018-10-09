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
import com.wanari.meetingtimer.navigation.NavigationEvent
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.Screen
import com.wanari.meetingtimer.navigation.ScreenManager
import com.wanari.meetingtimer.navigation.applyTo
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.ref.WeakReference

class RootActivity : BaseActivity() {
    private val screenManager by inject<ScreenManager>()
    private val schedulers by inject<Schedulers>()
    private val navigator by inject<Navigator>()

    private var currentScreenFragment: WeakReference<out ScreenFragment<*, *>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        handleIntent(intent)

        screenManager.dispatchNavigationEvent()
                .subscribeOn(schedulers.computation())
                .observeOn(schedulers.ui())
                .subscribe(this::navigate)
                .disposeOnDestroy()
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