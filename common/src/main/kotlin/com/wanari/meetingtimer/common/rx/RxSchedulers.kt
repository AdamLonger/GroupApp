package com.wanari.meetingtimer.common.rx

import android.os.HandlerThread
import android.os.Process
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.Executors

class RxSchedulers : Schedulers {

    private val backgroundThread: Scheduler
    private val looperThread: Scheduler

    init {
        val maxThreads = Math.max(1, Runtime.getRuntime().availableProcessors() - 1)
        backgroundThread = io.reactivex.schedulers.Schedulers.from(Executors.newFixedThreadPool(maxThreads))

        val handlerThread = HandlerThread("Rx-Looper-Thread", Process.THREAD_PRIORITY_BACKGROUND)
        handlerThread.start()
        looperThread = AndroidSchedulers.from(handlerThread.looper)
    }

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io() = backgroundThread

    override fun computation() = io.reactivex.schedulers.Schedulers.computation()

    override fun looper() = looperThread
}
