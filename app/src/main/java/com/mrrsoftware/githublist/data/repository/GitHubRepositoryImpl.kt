package com.mrrsoftware.githublist.data.repository

import com.mrrsoftware.githublist.data.remote.GithubService
import com.mrrsoftware.githublist.domain.entity.PullRequest
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.entity.User
import com.mrrsoftware.githublist.domain.repository.GitHubRepository

/**
 * Created by Welbert on 25/07/2024
 */

class GitHubRepositoryImpl(private val apiService: GithubService) : GitHubRepository {
    override suspend fun fetchRepositories(page: Int): RepositoryReposItems {
        val response = apiService.fetchRepositories(page)
        val items = response.body()?.items ?: emptyList()

        val listRepo = items.map {
            Repository(
                it.id.toString(), it.name, it.description, it.pullRequestsCount, it.starCount,
                User(it.owner.username, it.fullname, it.owner.avatarUrl)
            )
        }

        return RepositoryReposItems(response.body()?.incompleteResults ?: false, listRepo)
    }

    override suspend fun fetchPullRequests(
        ownerName: String,
        repositoryName: String
    ): List<PullRequest> {
        val response = apiService.fetchPullRequests(ownerName, repositoryName)
        val items = response.body() ?: emptyList()

        val listPullRequest = items.map {
            PullRequest(
                it.id, it.title, it.body ?: "", User(it.user.username, "", it.user.avatarUrl)
            )
        }

        return listPullRequest
    }


}
