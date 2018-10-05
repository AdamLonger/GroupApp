package com.wanari.meetingtimer.common.rx

import io.reactivex.Scheduler

/**
 * A {@code Scheduler} is an object that specifies an API for scheduling
 * units of work with or without delays or periodically.
 */
interface Schedulers {
    /**
     * A {@link Scheduler} which executes actions on the Android UI thread.
     *
     * @return a {@link Scheduler} intended for UI work
     */
    fun ui(): Scheduler

    /**
     * Returns a {@link Scheduler} intended for IO-bound work.
     * <p>
     * This can be used for asynchronously performing blocking IO.
     * <p>
     * Do not perform computational work on this scheduler. Use {@link #computation()} instead.
     * <p>
     * Unhandled errors will be delivered to the scheduler Thread's {@link Thread.UncaughtExceptionHandler}.
     *
     * @return a {@link Scheduler} intended for IO-bound work
     */
    fun io(): Scheduler

    /**
     * Returns a {@link Scheduler} intended for computational work.
     * <p>
     * This can be used for event-loops, processing callbacks and other computational work.
     * <p>
     * Do not perform IO-bound work on this scheduler. Use {@link #io()} instead.
     * <p>
     * Unhandled errors will be delivered to the scheduler Thread's {@link Thread.UncaughtExceptionHandler}.
     *
     * @return a {@link Scheduler} intended for computation-bound work
     */
    fun computation(): Scheduler

    /**
     * Returns a {@link Scheduler} intended for computational work a looper thread.
     * <p>
     * This can be used for specific api-s which require a looper.
     * <p>
     * Do not perform IO-bound work on this scheduler. Use {@link #io()} instead.
     * <p>
     * Unhandled errors will be delivered to the scheduler Thread's {@link Thread.UncaughtExceptionHandler}.
     *
     * @return a {@link Scheduler} intended for handler queues and looped api-s
     */
    fun looper(): Scheduler
}
