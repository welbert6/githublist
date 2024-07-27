package com.mrrsoftware.githublist.domain.usecase

import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.entity.User
import com.mrrsoftware.githublist.domain.repository.GitHubRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


/**
 * Created by Welbert on 26/07/2024
 */

class RepositoriesUseCaseTest {

    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var repositoriesUseCase: RepositoriesUseCase

    @Before
    fun setUp() {
        gitHubRepository = mock()
        repositoriesUseCase = RepositoriesUseCase(gitHubRepository)
    }

    @Test
    fun `should return RepositoryReposItems from GitHubRepository`() = runBlocking {
        val page = 1
        val repositoryList = listOf(Repository("id1", "name1", "description1",0,0,
            User("username","fullname","imageurl")
        ))
        val repositoryReposItems = RepositoryReposItems(
            hasMore = true,
            list = repositoryList
        )
        `when`(gitHubRepository.fetchRepositories(page)).thenReturn(repositoryReposItems)

        val result = repositoriesUseCase.execute(page)

        verify(gitHubRepository).fetchRepositories(page)
        assertEquals(repositoryReposItems, result)
    }
}
