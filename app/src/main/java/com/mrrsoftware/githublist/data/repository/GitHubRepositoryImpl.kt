package com.mrrsoftware.githublist.data.repository

import com.mrrsoftware.githublist.data.remote.GithubService
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems
import com.mrrsoftware.githublist.domain.entity.User

/**
 * Created by Welbert on 25/07/2024
 */

class GitHubRepositoryImpl(private val apiService: GithubService) : GitHubRepository {
    override suspend fun fetchRepositories(page: Int): RepositoryReposItems {
        val response = apiService.fetchRepositories(page)
        val items = response.body()?.items ?: emptyList()

        val listRepo = items.map {
            Repository(
                it.name, it.description, it.pullRequestsCount, it.starCount,
                User(it.owner.username, it.owner.avatarUrl)
            )
        }

        return RepositoryReposItems(response.body()?.incompleteResults ?: false, listRepo)

    }

}
