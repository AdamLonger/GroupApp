package com.wanari.meetingtimer.navigation

class DefaultNavigator(private val screenManager: ScreenManager) : Navigator {

    override fun navigateToHome() {
        val options = NavigationOptions(
                direction = Direction.REPLACE,
                purgeStack = true
        )

        navigateToHome(options)
    }

    override fun navigateToHome(options: NavigationOptions) {
        val screen = screenManager.defaultScreen
        screenManager.setScreen(screen, options)
    }

    override fun navigateTo(screen: Screen, options: NavigationOptions) {
        screenManager.setScreen(screen, options)
    }

    override fun navigateBack() {
        screenManager.goBack()
    }

    override fun navigateBackTo(screen: Screen) {
        val options = NavigationOptions(direction = Direction.BACKWARD)
        navigateBackTo(screen, options)
    }

    override fun navigateBackTo(screen: Screen, options: NavigationOptions) {
        screenManager.setScreen(screen, options)
    }
}
