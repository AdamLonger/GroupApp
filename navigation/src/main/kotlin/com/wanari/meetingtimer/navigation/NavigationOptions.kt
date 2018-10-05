package com.wanari.meetingtimer.navigation

data class NavigationOptions(
        val direction: Direction = Direction.FORWARD,
        val purgeStack: Boolean = false,
        val animation: Animation? = null,
        val sharedElements: SharedElementsHolder? = null
)
