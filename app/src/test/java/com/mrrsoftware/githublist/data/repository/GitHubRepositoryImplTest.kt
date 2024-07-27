package com.mrrsoftware.githublist.data.repository

import com.mrrsoftware.githublist.data.model.OwnerResponse
import com.mrrsoftware.githublist.data.model.PullRequestsResponse
import com.mrrsoftware.githublist.data.model.RepositoryItemResponse
import com.mrrsoftware.githublist.data.model.RepositoryResponse
import com.mrrsoftware.githublist.data.remote.GithubService
import com.mrrsoftware.githublist.domain.entity.PullRequest
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.entity.User
import com.mrrsoftware.githublist.domain.repository.GitHubRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

/**
 * Created by Welbert on 27/07/2024
 */
class GitHubRepositoryImplTest {

    private lateinit var apiService: GithubService
    private lateinit var gitHubRepository: GitHubRepository

    @Before
    fun setUp() {
        apiService = mock(GithubService::class.java)
        gitHubRepository = GitHubRepositoryImpl(apiService)
    }

    @Test
    fun `fetchRepositories should return RepositoryReposItems from apiService`() = runBlocking {
        val page = 1
        val items = listOf(
            RepositoryItemResponse(
                id = 2,
                name = "Repo1",
                description = "Description1",
                fullname = "fullname",
                owner = OwnerResponse( "fullname", "avatar_url"),
                pullRequestsCount = 10,
                starCount = 5
            )
        )
        val response = Response.success(RepositoryResponse( false, items))

        `when`(apiService.fetchRepositories(page)).thenReturn(response)

        val result = gitHubRepository.fetchRepositories(page)
        val expected = RepositoryReposItems(
            hasMore = false,
            list = listOf(
                Repository(
                    id = "2",
                    title = "Repo1",
                    description = "Description1",
                    pullRequestsCount = 10,
                    starsCount = 5,
                    user = User("fullname", "fullname", "avatar_url")
                )
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun `fetchRepositories should return empty list on error response`() = runBlocking {
        val page = 1
        val errorResponse = Response.error<RepositoryResponse>(
            500, ResponseBody.create("application/json".toMediaTypeOrNull(), "")
        )

        `when`(apiService.fetchRepositories(page)).thenReturn(errorResponse)

        val result = gitHubRepository.fetchRepositories(page)

        assertEquals(RepositoryReposItems(false, emptyList()), result)
    }


    @Test
    fun `fetchPullRequests should return list of PullRequests from apiService`() = runBlocking {
        val ownerName = "owner"
        val repoName = "repo"
        val pullRequestDtoList = listOf(
            PullRequestsResponse(1, "PR1", "Body1", OwnerResponse( "User One", "avatar_url"))
        )
        val response = Response.success(pullRequestDtoList)

        `when`(apiService.fetchPullRequests(ownerName, repoName)).thenReturn(response)

        val result = gitHubRepository.fetchPullRequests(ownerName, repoName)
        val expected = listOf(
            PullRequest(1, "PR1", "Body1", User("User One", "", "avatar_url"))
        )

        assertEquals(expected, result)
    }

    @Test
    fun `fetchPullRequests should return empty list on error response`() = runBlocking {
        val owner = "owner"
        val repo = "repo"
        val errorResponse = Response.error<List<PullRequestsResponse>>(
            500, ResponseBody.create("application/json".toMediaTypeOrNull(), "")
        )

        `when`(apiService.fetchPullRequests(owner,repo)).thenReturn(errorResponse)

        val result = gitHubRepository.fetchPullRequests(owner,repo)

        assertEquals(arrayListOf<RepositoryReposItems>(), result)
    }

}