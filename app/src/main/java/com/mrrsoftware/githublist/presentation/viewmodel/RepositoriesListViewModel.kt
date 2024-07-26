package com.mrrsoftware.githublist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrrsoftware.githublist.domain.usecase.RepositoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Welbert on 25/07/2024
 */
class RepositoriesListViewModel(
    private val repositoriesUseCase: RepositoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RepositoriesState>(RepositoriesState.HideLoading)
    val state = _state.asStateFlow()

    fun fetchRepositories(startIndex: Int = 0) = viewModelScope.launch {
        try {
            val repos = repositoriesUseCase.execute(startIndex)
            _state.value = RepositoriesState.HideLoading
            //    _state.value = RepositoriesState.ShowRepositories()
        } catch (ex: Exception) {
            _state.value = RepositoriesState.HideLoading
            //    _state.value = RepositoriesState.ShowFailed()
        }


    }

}