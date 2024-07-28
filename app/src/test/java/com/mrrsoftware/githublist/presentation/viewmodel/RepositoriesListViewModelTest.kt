package com.mrrsoftware.githublist.presentation.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.usecase.RepositoriesUseCase
import com.mrrsoftware.githublist.presentation.state.RepositoriesState
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
import org.koin.core.context.stopKoin


/**
 * Created by Welbert on 28/07/2024
 */


@OptIn(ExperimentalCoroutinesApi::class)
class RepositoriesListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testScope = TestScope()

    private lateinit var repositoriesUseCase: RepositoriesUseCase
    private lateinit var viewModel: RepositoriesListViewModel

    private val repository: Repository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repositoriesUseCase = mockk()
        viewModel = RepositoriesListViewModel(repositoriesUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `fetchRepositories should update state with ShowRepositories on success`() = runTest {
        val repositoryList = listOf(repository)
        val repositoryReposItems = RepositoryReposItems(false, repositoryList)

        coEvery { repositoriesUseCase.execute(any()) } returns repositoryReposItems

        viewModel.fetchRepositories()

        advanceUntilIdle()

        assertEquals(RepositoriesState.ShowRepositories(repositoryList), viewModel.state.value)
    }


    @Test
    fun `fetchRepositories should update state with ShowError on failure`() = testScope.runTest {
        coEvery { repositoriesUseCase.execute(any()) } throws Exception()

        viewModel.fetchRepositories()

        assertEquals(RepositoriesState.ShowError, viewModel.state.first())
    }

    @Test
    fun `fetchRepositories should not fetch if hasMore is false`() = testScope.runTest {
        val repositoryList = listOf(repository)
        val repositoryReposItems = RepositoryReposItems(false, repositoryList)

        coEvery { repositoriesUseCase.execute(any()) } returns repositoryReposItems

        viewModel.fetchRepositories()

        viewModel.fetchRepositories()

        coVerify(exactly = 1) { repositoriesUseCase.execute(any()) }
    }

    @Test
    fun `fetchRepositories should not fetch if isLoading is true`() = testScope.runTest {
        viewModel.isLoading = true

        viewModel.fetchRepositories()

        coVerify(exactly = 0) { repositoriesUseCase.execute(any()) }
    }
}
