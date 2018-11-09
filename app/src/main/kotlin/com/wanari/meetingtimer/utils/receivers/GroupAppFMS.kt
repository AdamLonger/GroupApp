package com.wanari.meetingtimer.utils.receivers

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import data.firebase.DeviceInfoManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

class GroupAppFMS : FirebaseMessagingService() {

    private val deviceInfoManger by inject<DeviceInfoManager>()
    private val disposables = CompositeDisposable()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.v("From: %s", remoteMessage.from)

        if (remoteMessage.data.isNotEmpty()) {
            Timber.v("Message data payload: $remoteMessage.data")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob()
            } else {
                // Handle message within 10 seconds
                //handleNow()
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Timber.v("Message Notification Body: ${remoteMessage.notification!!.body!!}")
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")

        disposables.add(deviceInfoManger.updateMessagingToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe())
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}