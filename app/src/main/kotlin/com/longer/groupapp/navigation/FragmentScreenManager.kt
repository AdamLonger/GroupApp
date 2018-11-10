package com.longer.groupapp.navigation

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import java.util.*

class FragmentScreenManager(override val defaultScreen: Screen) : ScreenManager {

    private val screenStack: Deque<Screen>
    private val navigationEventSubject: Subject<NavigationEvent>

    init {
        this.screenStack = ArrayDeque()

        val subject = BehaviorSubject.create<NavigationEvent>()
        this.navigationEventSubject = subject.toSerialized()
    }

    override fun goBack(): Boolean {
        val canGoBack = screenStack.size > 1

        if (!canGoBack) {
            return false
        }

        screenStack.pop() // remove current screen

        val screen = screenStack.peek() // get next screen
        // replace previous screen instance with "fresh" screen
        val options = NavigationOptions(direction = Direction.REPLACE)

        setScreen(screen, options)

        return true
    }

    override fun setScreen(screen: Screen, options: NavigationOptions) {
        if (options.purgeStack) {
            screenStack.clear()
        } else if (options.direction === Direction.BACKWARD) {
            while (!screenStack.isEmpty()) {
                if (screenStack.pop() == screen) {
                    break
                }
            }
        } else if (!screenStack.isEmpty() && options.direction == Direction.REPLACE) {
            screenStack.pop()
        }

        screenStack.push(screen)

        val event = NavigationEvent(screen, options)
        navigationEventSubject.onNext(event)

        Timber.d("currentStack: %s", screenStack)
    }

    override fun dispatchNavigationEvent(): Flowable<NavigationEvent> {
        return navigationEventSubject.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getStackSite(): Int = screenStack.size
}
