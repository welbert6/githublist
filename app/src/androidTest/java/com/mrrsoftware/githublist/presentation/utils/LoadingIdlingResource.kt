package com.mrrsoftware.githublist.presentation.utils

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.matcher.ViewMatchers

/**
 * Created by Welbert on 28/07/2024
 */

class LoadingIdlingResource(private val loadingViewId: Int) : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = "LoadingIdlingResource"

    override fun isIdleNow(): Boolean {
        var isIdle = false
        Espresso.onView(ViewMatchers.withId(loadingViewId))
            .check { view, _ ->
                isIdle = if (view != null) {
                    (view.visibility == View.GONE)
                } else {
                    true
                }
                if (isIdle) {
                    callback?.onTransitionToIdle()
                }
            }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}