package com.wanari.meetingtimer.data.utils

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor

fun <T> FlowableProcessor<T>.wrapInFlowable(): Flowable<T> = Flowable.create({ emitter ->
    val disposable = subscribe(
            { item -> emitter.takeIf { !it.isCancelled }?.onNext(item) },
            { error -> emitter.takeIf { !it.isCancelled }?.onError(error) }
    )

    emitter.setCancellable(disposable::dispose)
}, BackpressureStrategy.BUFFER)
