package com.wanari.meetingtimer

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import com.wanari.meetingtimer.common.di.createScreen
import com.wanari.meetingtimer.common.rx.Schedulers
import com.wanari.meetingtimer.common.ui.BaseActivity
import com.wanari.meetingtimer.common.ui.ForegroundManager
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.NavigationEvent
import com.wanari.meetingtimer.navigation.ScreenManager
import com.wanari.meetingtimer.navigation.applyTo
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.ref.WeakReference

class RootActivity : BaseActivity() {

    private val screenManager by inject<ScreenManager>()
    private val schedulers by inject<Schedulers>()

    private var currentScreenFragment: WeakReference<out ScreenFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

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
                    fragment.sharedElementEnterTransition = TransitionInflater
                            .from(this@RootActivity)
                            .inflateTransition(sharedHolder.transitionRes)
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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    companion object {
        private const val FRAGMENT_TAG = "SCREEN_FRAGMENT_TAG"
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
