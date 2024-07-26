package com.mrrsoftware.githublist.presentation.state

import com.mrrsoftware.githublist.domain.entity.Repository

/**
 * Created by Welbert on 25/07/2024
 */

sealed class RepositoriesState {
    data class ShowRepositories(val list: List<Repository>) : RepositoriesState()
    data object ShowLoading : RepositoriesState()
    data object HideLoading : RepositoriesState()
}
