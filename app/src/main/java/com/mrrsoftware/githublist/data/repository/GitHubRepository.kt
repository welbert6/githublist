package com.mrrsoftware.githublist.data.repository

import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems

/**
 * Created by Welbert on 25/07/2024
 */
interface GitHubRepository {
    suspend fun fetchRepositories(page: Int): RepositoryReposItems
}