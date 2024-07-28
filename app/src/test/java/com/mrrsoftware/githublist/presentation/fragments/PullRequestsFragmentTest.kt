package com.mrrsoftware.githublist.presentation.fragments

import android.os.Build
import android.view.View
import androidx.test.core.app.launchActivity
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity
import com.mrrsoftware.githublist.presentation.state.PullRequestState
import com.mrrsoftware.githublist.presentation.viewmodel.PullRequestsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

/**
 * Created by Welbert on 28/07/2024
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PullRequestsFragmentTest : KoinTest {

    private val viewModel: PullRequestsViewModel = mockk(relaxed = true)

    @Before
    fun setUp() {
        stopKoin()

        val testModule = module {
            single { viewModel }
        }

        startKoin {
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test ShowPullRequests state displays a list of pull requests`() = runTest {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.ShowPullRequests(listOf(
            mockk(relaxed = true))))
        every { viewModel.state } returns stateFlow

        val scenario = launchActivity<PullRequestsActivity>()
        scenario.onActivity { activity ->
            val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
            activity.supportFragmentManager.beginTransaction()
                .add(fragment, null)
                .commitNow()
            assert(fragment.binding.rcPulls.adapter?.itemCount == 1)
        }
    }

    @Test
    fun `test EmptyPullRequest state should not displays a list of pull requests`() = runTest {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.ShowEmptyPullRequests)
        every { viewModel.state } returns stateFlow

        val scenario = launchActivity<PullRequestsActivity>()
        scenario.onActivity { activity ->
            val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
            activity.supportFragmentManager.beginTransaction()
                .add(fragment, null)
                .commitNow()

            stateFlow.value = PullRequestState.ShowEmptyPullRequests
            assert(fragment.binding.rcPulls.adapter == null)
        }
    }

    @Test
    fun `test ShowError state displays a Toast`() = runTest {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.ShowError)
        every { viewModel.state } returns stateFlow

        val scenario = launchActivity<PullRequestsActivity>()
        scenario.onActivity { activity ->
            val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
            activity.supportFragmentManager.beginTransaction()
                .add(fragment, null)
                .commitNow()

            stateFlow.value = PullRequestState.ShowError

            val latestToast = ShadowToast.getLatestToast()
            assert(latestToast != null)
            assert(ShadowToast.getTextOfLatestToast() == activity.getString(R.string.error_generico_api))
        }
    }

    @Test
    fun `test should ShowError when missing some argument in fragment `() = runTest {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.ShowEmptyPullRequests)
        every { viewModel.state } returns stateFlow

        val scenario = launchActivity<PullRequestsActivity>()
        scenario.onActivity { activity ->
            val fragment = PullRequestsFragment()
            activity.supportFragmentManager.beginTransaction()
                .add(fragment, null)
                .commitNow()



            val latestToast = ShadowToast.getLatestToast()
            assert(latestToast != null)
            assert(ShadowToast.getTextOfLatestToast() == activity.getString(R.string.error_generico_api))
        }
    }

    @Test
    fun `test HideLoading state hides loading view`() = runTest {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.HideLoading)
        every { viewModel.state } returns stateFlow

        val scenario = launchActivity<PullRequestsActivity>()
        scenario.onActivity { activity ->
            val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
            activity.supportFragmentManager.beginTransaction()
                .add(fragment, null)
                .commitNow()

            stateFlow.value = PullRequestState.HideLoading

            assert(fragment.binding.loading.visibility == View.GONE)
        }
    }

}
