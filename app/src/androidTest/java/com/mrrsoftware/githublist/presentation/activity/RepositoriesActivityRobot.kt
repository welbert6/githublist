package com.mrrsoftware.githublist.presentation.activity

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.mrrsoftware.githublist.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf

/**
 * Created by Welbert on 28/07/2024
 */
class RepositoriesActivityRobot {

    fun checkRepositoriesCount(expectedCount: Int) {
        onView(withId(R.id.rcRepos))
            .check(matches(hasChildCount(expectedCount)))
    }

    fun checkLoadingVisible() {
        onView(withId(R.id.loading))
            .check(matches(isDisplayed()))
    }

    fun checkLoadingGone() {
        onView(withId(R.id.loading))
            .check(matches(not(isDisplayed())))
    }

    fun clickRepositoryItem(position: Int) {
        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.rcRepos),
                childAtPosition(
                    withId(R.id.main),
                    position
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                click()
            )
        )

        val viewGroup = onView(
            Matchers.allOf(
                ViewMatchers.withParent(
                    Matchers.allOf(
                        withId(R.id.container),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
