package com.mrrsoftware.githublist.presentation.activity

import android.content.Intent
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mrrsoftware.githublist.presentation.fragments.PullRequestsFragment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Welbert on 28/07/2024
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class PullRequestsActivityTest {

    @Test
    fun `test activity initialization`() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), PullRequestsActivity::class.java).apply {
            putExtra(PullRequestsActivity.REPOSITORY, "sampleRepo")
            putExtra(PullRequestsActivity.OWNER, "sampleOwner")
            putExtra(PullRequestsActivity.ISSUES, 10)
        }

        val activity = Robolectric.buildActivity(PullRequestsActivity::class.java, intent).setup().get()

        assertEquals("sampleRepo", activity.title)

        val fragment = activity.supportFragmentManager.findFragmentById(com.mrrsoftware.githublist.R.id.container)
        assertNotNull(fragment)
        assertTrue(fragment is PullRequestsFragment)

        fragment as PullRequestsFragment
        assertEquals("sampleOwner", fragment.arguments?.getString(PullRequestsActivity.OWNER))
        assertEquals("sampleRepo", fragment.arguments?.getString(PullRequestsActivity.REPOSITORY))
        assertEquals(10, fragment.arguments?.getInt(PullRequestsActivity.ISSUES))
    }
}
