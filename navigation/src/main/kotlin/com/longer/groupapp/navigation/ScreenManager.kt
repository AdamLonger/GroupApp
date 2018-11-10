package com.longer.groupapp.navigation

import io.reactivex.Flowable

/**
 * Manages the screen stack and history.
 */
interface ScreenManager {
    /**
     * Gets the default screen.
     *
     * @return the starting screen
     */
    val defaultScreen: Screen

    /**
     * Goes back to the previous screen. Think "back button".
     *
     * @return false if going back is not possible
     */
    fun goBack(): Boolean

    /**
     * Goes to the requested screen. Goes back or forward or replaces the current screen
     * depending on the [Direction].
     *
     * @param screen  The screen where you want to move to
     * @param options Settings for the navigation
     */
    fun setScreen(screen: Screen, options: NavigationOptions)

    /**
     * The screen manager does not performs any navigation. It updates the history and dispatches
     * the events to a consumer. The consumer can do the actual traversal between screens.
     *
     * @return a flow of navigation events.
     */
    fun dispatchNavigationEvent(): Flowable<NavigationEvent>

    fun getStackSite():Int
}
