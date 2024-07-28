package com.mrrsoftware.githublist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrrsoftware.githublist.domain.entity.PullRequest
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.usecase.PullRequestsUseCase
import com.mrrsoftware.githublist.presentation.state.PullRequestState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


/**
 * Created by Welbert on 28/07/2024
 */


@OptIn(ExperimentalCoroutinesApi::class)
class PullRequestsViewModelTests {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testScope = TestScope()

    private lateinit var pullRequestsUseCase: PullRequestsUseCase
    private lateinit var viewModel: PullRequestsViewModel

    private val pullRequest : PullRequest = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        pullRequestsUseCase = mockk()
        viewModel = PullRequestsViewModel(pullRequestsUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `fetchPullRequest should update state with ShowPullRequests on success`() = runTest {
        val listPullRequest  = listOf(pullRequest)

        coEvery { pullRequestsUseCase.execute(any(),any()) } returns listPullRequest

        viewModel.fetchPullRequests("ownerName","repositoryName")

        advanceUntilIdle()

        assertEquals(PullRequestState.ShowPullRequests(listPullRequest), viewModel.state.value)
    }

    @Test
    fun `fetchPullRequest should update state with ShowEmptyPullRequests when response is empty`() = runTest {
        coEvery { pullRequestsUseCase.execute(any(),any()) } returns emptyList()

        viewModel.fetchPullRequests("ownerName","repositoryName")

        advanceUntilIdle()

        assertEquals(PullRequestState.ShowEmptyPullRequests, viewModel.state.value)
    }


    @Test
    fun `fetchPullRequests should update state with ShowError on failure`() = testScope.runTest {
        coEvery { pullRequestsUseCase.execute(any(),any()) } throws Exception()

        viewModel.fetchPullRequests("ownerName","repoName")

        assertEquals(PullRequestState.ShowError, viewModel.state.first())
    }


}
