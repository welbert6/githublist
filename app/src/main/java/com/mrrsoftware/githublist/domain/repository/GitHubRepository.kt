package com.mrrsoftware.githublist.domain.repository

import com.mrrsoftware.githublist.domain.entity.PullRequest
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems

/**
 * Created by Welbert on 25/07/2024
 */
interface GitHubRepository {
    suspend fun fetchRepositories(page: Int): RepositoryReposItems
    suspend fun fetchPullRequests(ownerName : String, repositoryName : String) : List<PullRequest>
}