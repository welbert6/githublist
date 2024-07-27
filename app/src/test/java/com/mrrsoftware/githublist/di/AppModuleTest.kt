package com.mrrsoftware.githublist.di



import com.mrrsoftware.githublist.data.remote.GithubService
import com.mrrsoftware.githublist.data.repository.GitHubRepositoryImpl
import com.mrrsoftware.githublist.domain.repository.GitHubRepository
import com.mrrsoftware.githublist.domain.usecase.PullRequestsUseCase
import com.mrrsoftware.githublist.domain.usecase.RepositoriesUseCase
import com.mrrsoftware.githublist.presentation.viewmodel.PullRequestsViewModel
import com.mrrsoftware.githublist.presentation.viewmodel.RepositoriesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

/**
 * Created by Welbert on 27/07/2024
 */


class AppModuleTest : KoinTest {

    private lateinit var appModule: Module

    @Before
    fun setUp() {
        appModule = module {
            single { provideRetrofit() }
            single { get<Retrofit>().create(GithubService::class.java) }
            single<GitHubRepository> { GitHubRepositoryImpl(get()) }

            factory { RepositoriesUseCase(get()) }
            viewModel { RepositoriesListViewModel(get()) }

            factory { PullRequestsUseCase(get()) }
            viewModel { PullRequestsViewModel(get()) }
        }

        startKoin {
            modules(appModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun `test dependencies are provided`() {
        val retrofit: Retrofit by inject()
        val githubService: GithubService by inject()
        val gitHubRepository: GitHubRepository by inject()
        val repositoriesUseCase: RepositoriesUseCase by inject()
        val pullRequestsUseCase: PullRequestsUseCase by inject()
        val repositoriesListViewModel: RepositoriesListViewModel by inject()
        val pullRequestsViewModel: PullRequestsViewModel by inject()

        assertNotNull(retrofit)
        assertNotNull(githubService)
        assertNotNull(gitHubRepository)
        assertNotNull(repositoriesUseCase)
        assertNotNull(pullRequestsUseCase)
        assertNotNull(repositoriesListViewModel)
        assertNotNull(pullRequestsViewModel)
    }
}
