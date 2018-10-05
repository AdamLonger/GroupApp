package com.wanari.meetingtimer.navigation

/**
 * Framework independent navigator. Provides navigation methods between screens.
 */
interface Navigator {
    /**
     * Navigates to the starting screen.
     * Calls [.navigateToHome] with null.
     */
    fun navigateToHome()

    /**
     * Navigates to the starting screen.
     *
     * @param options Settings for the navigation
     */
    fun navigateToHome(options: NavigationOptions)

    /**
     * Navigates forward to the requested screen with custom navigation options.
     *
     * @param screen  The screen where you want to navigate to
     * @param options Settings for the navigation.
     */
    fun navigateTo(screen: Screen, options: NavigationOptions = NavigationOptions())

    /**
     * Navigates back to the previous screen.
     */
    fun navigateBack()

    /**
     * Navigates back to the requested screen.
     *
     * @param screen The screen where you want to navigate to
     */
    fun navigateBackTo(screen: Screen)

    /**
     * Navigates back to the requested screen with custom navigation options.
     *
     * @param screen  The screen where you want to navigate to
     * @param options Settings for the navigation
     */
    fun navigateBackTo(screen: Screen, options: NavigationOptions)
}
