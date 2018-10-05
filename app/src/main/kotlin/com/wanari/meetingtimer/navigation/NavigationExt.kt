package com.wanari.meetingtimer.navigation

import android.support.v4.app.FragmentTransaction

internal fun Animation.applyTo(transaction: FragmentTransaction): FragmentTransaction = when(this) {
    is Animation.SimpleAnimation ->
        transaction.setCustomAnimations(enter, exit)
    is Animation.SimpleWithPopAnimation ->
        transaction.setCustomAnimations(enter, exit, popEnter, popExit)
}

internal fun SharedElementsHolder.applyTo(transaction: FragmentTransaction) {
    this.elementMap.entries.forEach { (name, view) ->
        view.get()?.let {
            transaction.addSharedElement(it, name)
        }
    }
}

fun NavigationOptions.applyTo(transaction: FragmentTransaction) {
    animation?.applyTo(transaction)
    sharedElements?.applyTo(transaction)
}
