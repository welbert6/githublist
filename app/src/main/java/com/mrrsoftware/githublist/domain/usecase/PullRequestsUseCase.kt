package com.mrrsoftware.githublist.domain.usecase

import com.mrrsoftware.githublist.domain.repository.GitHubRepository
import com.mrrsoftware.githublist.domain.entity.PullRequest

/**
 * Created by Welbert on 26/07/2024
 */
class PullRequestsUseCase(private val gitHubRepository: GitHubRepository) {
    suspend fun execute(ownerName: String, repositoryName: String): List<PullRequest> {
        return gitHubRepository.fetchPullRequests(ownerName, repositoryName)
    }
}