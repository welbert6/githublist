package com.mrrsoftware.githublist.domain.usecase

import com.mrrsoftware.githublist.domain.repository.GitHubRepository
import com.mrrsoftware.githublist.domain.entity.RepositoryReposItems

/**
 * Created by Welbert on 25/07/2024
 */

class RepositoriesUseCase(private val gitHubRepository: GitHubRepository) {
    suspend fun execute(page: Int): RepositoryReposItems {
        return gitHubRepository.fetchRepositories(page)
    }
}
