package com.mrrsoftware.githublist.presentation.activity


import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.entity.User
import com.mrrsoftware.githublist.domain.usecase.RepositoriesUseCase
import com.mrrsoftware.githublist.presentation.state.RepositoriesState
import com.mrrsoftware.githublist.presentation.viewmodel.RepositoriesListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class RepositoriesActivityTest : KoinTest {

    @MockK
    private val repositoriesUseCase: RepositoriesUseCase = mockk(relaxed = true)

    @MockK
    private val viewModel: RepositoriesListViewModel = mockk(relaxed = true)

    @Before
    fun setUp() {
        stopKoin()
        MockKAnnotations.init(this, relaxed = true)

        val testModule = module {
            single { repositoriesUseCase }
            single { viewModel }
        }

        startKoin {
            modules(testModule)
        }

        val repository = Repository("Repo 1", "Description 1", "Owner 1", 0, 10, User("", "", ""))
        val repositoryReposItems = RepositoryReposItems(true, listOf(repository))

        coEvery { repositoriesUseCase.execute(any()) } returns repositoryReposItems

        coEvery { viewModel.fetchRepositories() } returns Job()

    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test RepositoriesActivity displays repository list`() = runTest {
        val stateFlow = MutableStateFlow<RepositoriesState>(
            RepositoriesState.ShowRepositories(
                listOf(mockk(relaxed = true))
            )
        )
        every { viewModel.state } returns stateFlow

        val intent =
            Intent(ApplicationProvider.getApplicationContext(), RepositoriesActivity::class.java)



        ActivityScenario.launch<RepositoriesActivity>(intent).use { scenario ->
            scenario.onActivity { activity ->

                val recyclerView =
                    activity.findViewById<RecyclerView>(com.mrrsoftware.githublist.R.id.rcRepos)
                assert(recyclerView.adapter?.itemCount == 1)
            }
        }

    }

    @Test
    fun `test HideLoading state hides loading view`() = runTest {
        val stateFlow = MutableStateFlow<RepositoriesState>(RepositoriesState.HideLoading)
        every { viewModel.state } returns stateFlow

        val intent =
            Intent(ApplicationProvider.getApplicationContext(), RepositoriesActivity::class.java)
        ActivityScenario.launch<RepositoriesActivity>(intent).use { scenario ->
            scenario.onActivity { activity ->
                stateFlow.value = RepositoriesState.HideLoading

                val loadingView = activity.binding.loading
                assert(loadingView.visibility == View.GONE)
            }
        }
    }

    @Test
    fun `test ShowError state displays a Toast`() {
        val stateFlow = MutableStateFlow<RepositoriesState>(RepositoriesState.ShowError)
        every { viewModel.state } returns stateFlow

        val intent = Intent(ApplicationProvider.getApplicationContext(), RepositoriesActivity::class.java)
        ActivityScenario.launch<RepositoriesActivity>(intent).use { scenario ->
            scenario.onActivity { activity ->
                stateFlow.value = RepositoriesState.ShowError

                val lastToast = ShadowToast.getLatestToast()
                assert(lastToast != null)
                assert(ShadowToast.getTextOfLatestToast() == activity.getString(com.mrrsoftware.githublist.R.string.error_generico_api))
            }
        }
    }
}