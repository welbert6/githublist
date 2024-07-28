package com.mrrsoftware.githublist.presentation.fragments


import androidx.core.os.bundleOf
import androidx.test.core.app.launchActivity
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity
import com.mrrsoftware.githublist.presentation.state.PullRequestState
import com.mrrsoftware.githublist.presentation.utils.LoadingIdlingResource
import com.mrrsoftware.githublist.presentation.viewmodel.PullRequestsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Created by Welbert on 28/07/2024
 */

@RunWith(AndroidJUnit4::class)
class PullRequestsFragmentInstrumentedTest : KoinTest {

    private val viewModel: PullRequestsViewModel = mockk(relaxed = true)

    private val robot = PullRequestsFragmentRobot()

    private val loadingIdlingResource = LoadingIdlingResource(R.id.loading)

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
    fun testShowPullRequestsStateDisplaysList() {
        loadingIdlingResource.registerIdleTransitionCallback {
            robot.checkPullRequestsListIsDisplayed()
        }

        val stateFlow = MutableStateFlow<PullRequestState>(
            PullRequestState.ShowPullRequests(
                listOf(
                    mockk(relaxed = true)
                )
            )
        )
        every { viewModel.state } returns stateFlow



        launchActivity<PullRequestsActivity>().use { scenario ->
            scenario.onActivity { activity ->
                val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
                activity.supportFragmentManager.beginTransaction()
                    .add(fragment, null)
                    .commitNow()
            }
        }

        IdlingRegistry.getInstance().unregister(loadingIdlingResource)

    }

    @Test
    fun testEmptyPullRequestStateDisplaysMessage() {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.ShowEmptyPullRequests)
        every { viewModel.state } returns stateFlow


        launchActivity<PullRequestsActivity>( activityOptions = bundleOf(
            "owner" to "ownerName",
            "repo" to "repoName",
            "issues" to 0
        )).use { scenario ->

            scenario.onActivity { activity ->
                loadingIdlingResource.registerIdleTransitionCallback {
                    robot.checkToastIsDisplayed("Tem que falhar")
                }


                val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
                activity.supportFragmentManager.beginTransaction()
                    .add(fragment, null)
                    .commitNow()

            }
        }


        IdlingRegistry.getInstance().unregister(loadingIdlingResource)

    }

    @Test
    fun testShowErrorStateDisplaysToast() {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.ShowError)
        every { viewModel.state } returns stateFlow

        launchActivity<PullRequestsActivity>().use { scenario ->

            loadingIdlingResource.registerIdleTransitionCallback {
                robot.checkEmptyMessageIsDisplayed()
            }

            scenario.onActivity { activity ->
                val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
                activity.supportFragmentManager.beginTransaction()
                    .add(fragment, null)
                    .commitNow()


            }
        }

        IdlingRegistry.getInstance().unregister(loadingIdlingResource)
    }

    @Test
    fun testHideLoadingStateHidesLoadingView() {
        val stateFlow = MutableStateFlow<PullRequestState>(PullRequestState.HideLoading)
        every { viewModel.state } returns stateFlow

        loadingIdlingResource.registerIdleTransitionCallback {
            robot.checkLoadingIsGone()
        }

        launchActivity<PullRequestsActivity>().use { scenario ->
            scenario.onActivity { activity ->
                val fragment = PullRequestsFragment.newInstance("ownerName", "repoName", 0)
                activity.supportFragmentManager.beginTransaction()
                    .add(fragment, null)
                    .commitNow()


            }
        }

        IdlingRegistry.getInstance().unregister(loadingIdlingResource)
    }
}
