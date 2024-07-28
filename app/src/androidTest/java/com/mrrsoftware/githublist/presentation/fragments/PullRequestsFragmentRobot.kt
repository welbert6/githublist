package com.mrrsoftware.githublist.presentation.fragments

import android.view.WindowManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.mrrsoftware.githublist.R
import org.junit.internal.matchers.TypeSafeMatcher

/**
 * Created by Welbert on 28/07/2024
 */


class PullRequestsFragmentRobot {

    fun checkPullRequestsListIsDisplayed() = apply {
        onView(withId(R.id.rcPulls))
            .check(matches(isDisplayed()))
    }

    fun checkEmptyMessageIsDisplayed() = apply {
        onView(withId(R.id.txtTotalCount))
            .check(matches(withText("Nenhuma pull request aberta")))
    }

    fun checkLoadingIsGone() = apply {
        onView(withId(R.id.loading))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    fun checkToastIsDisplayed(message: String) = apply {
        onView(withText(message))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }
}

class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun matchesSafely(item: Root): Boolean {
        val type = item.windowLayoutParams.get().type
        return type == WindowManager.LayoutParams.TYPE_TOAST
    }

    override fun describeTo(description: org.hamcrest.Description?) {
        description?.appendText("is toast")
    }
}
