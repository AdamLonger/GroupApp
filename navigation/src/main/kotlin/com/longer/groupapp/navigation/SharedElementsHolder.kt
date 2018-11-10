package com.longer.groupapp.navigation

import android.view.View
import java.lang.ref.WeakReference

class SharedElementsHolder {
    val elementMap = mutableMapOf<String, WeakReference<View>>()
    var transitionRes = android.R.transition.no_transition

    /**
     * Used with custom Transitions to map a View from a removed or hidden
     * Fragment to a View from a shown or added Fragment.
     * <var>sharedElement</var> must have a unique transitionName in the View hierarchy.
     *
     * @param sharedElement A View in a disappearing Fragment to match with a View in an
     * appearing Fragment.
     * @param name The transitionName for a View in an appearing Fragment to match to the shared
     * element.
     * @see Fragment.setSharedElementReturnTransition
     * @see Fragment.setSharedElementEnterTransition
     */
    fun addSharedElement(sharedElement: View, name: String) {
        elementMap[name] = WeakReference(sharedElement)
    }
}
