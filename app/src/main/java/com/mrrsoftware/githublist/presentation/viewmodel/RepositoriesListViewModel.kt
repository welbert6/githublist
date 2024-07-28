package com.mrrsoftware.githublist.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrrsoftware.githublist.domain.usecase.RepositoriesUseCase
import com.mrrsoftware.githublist.presentation.state.RepositoriesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Welbert on 25/07/2024
 */
class RepositoriesListViewModel(
    private val repositoriesUseCase: RepositoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RepositoriesState>(RepositoriesState.ShowLoading)
    val state = _state.asStateFlow()

    private var hasMore = true
    private var currentPage = 0

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var isLoading = false


    fun fetchRepositories() = viewModelScope.launch {
        if (!hasMore || isLoading) {
            return@launch
        }
        isLoading = true
        try {
            val repos = repositoriesUseCase.execute(currentPage)
            currentPage += 1
            hasMore = repos.hasMore
            _state.value = RepositoriesState.HideLoading
            _state.value = RepositoriesState.ShowRepositories(repos.list)
            isLoading = false
        } catch (ex: Exception) {
            _state.value = RepositoriesState.HideLoading
            _state.value = RepositoriesState.ShowError
            isLoading = false
        }


    }

}