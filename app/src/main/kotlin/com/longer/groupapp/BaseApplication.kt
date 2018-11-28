package com.longer.groupapp

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import com.jakewharton.threetenabp.AndroidThreeTen
import com.longer.groupapp.common.di.schedulerModule
import com.longer.groupapp.di.configModule
import com.longer.groupapp.di.navigatorModule
import com.longer.groupapp.presentation.di.screenModule
import com.longer.groupapp.utils.KoinLogger
import data.di.dataModule
import data.di.firebaseModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import timber.log.Timber

private const val DEFAULT_FONT_PATH = "fonts/WorkSans-Regular.ttf"

const val PRIORITY_TAG = "priority"
const val DEFAULT_TAG = "default"

class BaseApplication : Application() {

    private val koinModules: List<Module> = mutableListOf(
            configModule,
            schedulerModule,
            navigatorModule,
            dataModule,
            firebaseModule,
            screenModule
    )

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        RxJavaPlugins.setErrorHandler { Timber.e(it) }

        startKoin(
                context = this,
                modules = koinModules,
                logger = KoinLogger())

        initNotificationChannel()

        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath(DEFAULT_FONT_PATH)
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build()
        )

        AndroidThreeTen.init(this)
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val name = getString(R.string.app_name)
        val channel = NotificationChannel(getString(R.string.default_notification_channel_id),
                name + DEFAULT_TAG,
                NotificationManager.IMPORTANCE_DEFAULT)

        val channelPriority = NotificationChannel(packageName + PRIORITY_TAG,
                name + PRIORITY_TAG,
                NotificationManager.IMPORTANCE_HIGH)

        val channelList = listOf(channel, channelPriority)

        channelList.forEach {
            it.enableLights(true)
            it.lightColor = ContextCompat.getColor(this, R.color.colorPrimary)
            it.enableVibration(true)
            it.lockscreenVisibility = Notification.VISIBILITY_SECRET
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannels(channelList)
    }
}
