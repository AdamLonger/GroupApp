package com.wanari.meetingtimer.navigation

import android.support.annotation.AnimRes
import android.support.annotation.AnimatorRes

sealed class Animation {

    data class SimpleAnimation(
            @AnimRes @AnimatorRes val enter: Int,
            @AnimRes @AnimatorRes val exit: Int
    ) : Animation()

    data class SimpleWithPopAnimation(
            @AnimRes @AnimatorRes val enter: Int,
            @AnimRes @AnimatorRes val exit: Int,
            @AnimRes @AnimatorRes val popEnter: Int,
            @AnimRes @AnimatorRes val popExit: Int
    ) : Animation()

    companion object {

        /**
         * Set specific animation resources to run for the fragments that are
         * entering and exiting in this transaction. These animations will not be
         * played when popping the back stack.
         *
         * @param enter An animation or animator resource ID used for the enter animation on the
         * view of the fragment being added or attached.
         * @param exit An animation or animator resource ID used for the exit animation on the
         * view of the fragment being removed or detached.
         */
        fun fromAnimations(@AnimatorRes @AnimRes enter: Int,
                           @AnimatorRes @AnimRes exit: Int): Animation {
            return SimpleAnimation(enter, exit)
        }

        /**
         * Set specific animation resources to run for the fragments that are
         * entering and exiting in this transaction. The `popEnter`
         * and `popExit` animations will be played for enter/exit
         * operations specifically when popping the back stack.
         *
         * @param enter An animation or animator resource ID used for the enter animation on the
         * view of the fragment being added or attached.
         * @param exit An animation or animator resource ID used for the exit animation on the
         * view of the fragment being removed or detached.
         * @param popEnter An animation or animator resource ID used for the enter animation on the
         * view of the fragment being readded or reattached caused by
         * [FragmentManager.popBackStack] or similar methods.
         * @param popExit An animation or animator resource ID used for the enter animation on the
         * view of the fragment being removed or detached caused by
         * [FragmentManager.popBackStack] or similar methods.
         */
        fun fromAnimations(@AnimatorRes @AnimRes enter: Int,
                           @AnimatorRes @AnimRes exit: Int,
                           @AnimatorRes @AnimRes popEnter: Int,
                           @AnimatorRes @AnimRes popExit: Int): Animation {
            return SimpleWithPopAnimation(enter, exit, popEnter, popExit)
        }
    }
}
