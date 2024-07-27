package com.mrrsoftware.githublist.domain.usecase

import com.mrrsoftware.githublist.domain.entity.PullRequest
import com.mrrsoftware.githublist.domain.entity.User
import com.mrrsoftware.githublist.domain.repository.GitHubRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Created by Welbert on 26/07/2024
 */


class PullRequestsUseCaseTest {

    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var pullRequestsUseCase: PullRequestsUseCase

    @Before
    fun setUp() {
        gitHubRepository = Mockito.mock()
        pullRequestsUseCase = PullRequestsUseCase(gitHubRepository)
    }


    @Test
    fun `should return List of PullRequests from GitHubRepository`() = runBlocking {
        val ownerName = "ownerName"
        val repositoryName = "repositoryName"
        val pullRequestList = listOf(
            PullRequest(2, "title1", "body1",
                User("username", "fullname", "imageurl"))
        )

        `when`(gitHubRepository.fetchPullRequests(ownerName, repositoryName))
            .thenReturn(pullRequestList)

        val result = pullRequestsUseCase.execute(ownerName, repositoryName)

        verify(gitHubRepository).fetchPullRequests(ownerName, repositoryName)
        assertEquals(pullRequestList, result)
    }
}
