package com.mrrsoftware.githublist.domain.usecase

import com.mrrsoftware.githublist.data.repository.GitHubRepository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems

/**
 * Created by Welbert on 26/07/2024
 */
class PullRequestsUseCase(private val gitHubRepository: GitHubRepository) {
    suspend fun execute(page: Int): RepositoryReposItems {
        return gitHubRepository.fetchRepositories(page)
    }
}