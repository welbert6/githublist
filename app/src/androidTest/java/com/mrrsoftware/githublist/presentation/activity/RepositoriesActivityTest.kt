package com.mrrsoftware.githublist.presentation.activity

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

/**
 * Created by Welbert on 28/07/2024
 */

@LargeTest
@RunWith(AndroidJUnit4::class)
class RepositoriesActivityTest {

    private val robot = RepositoriesActivityRobot()

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RepositoriesActivity::class.java)

    @Test
    fun testClickOnRepositoryItemOpensPullRequestsActivity()  {
        robot.clickRepositoryItem(0)
    }


}