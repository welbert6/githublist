package com.mrrsoftware.githublist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrrsoftware.githublist.domain.usecase.PullRequestsUseCase
import com.mrrsoftware.githublist.presentation.state.PullRequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Welbert on 26/07/2024
 */
class PullRequestsViewModel(
    private val useCase: PullRequestsUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<PullRequestState>(PullRequestState.ShowLoading)
    val state = _state.asStateFlow()


    fun fetchPullRequests(ownerName: String, repositoryName: String) = viewModelScope.launch {
        try {
            val pullRequests = useCase.execute(ownerName, repositoryName)
            _state.value = PullRequestState.HideLoading

            if (pullRequests.isEmpty()){
               _state.value = PullRequestState.ShowEmptyPullRequests
            }else{
                _state.value = PullRequestState.ShowPullRequests(pullRequests)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            _state.value = PullRequestState.HideLoading
            _state.value = PullRequestState.ShowError
        }


    }

}