package com.mrrsoftware.githublist.presentation.state

import com.mrrsoftware.githublist.domain.entity.PullRequest

/**
 * Created by Welbert on 26/07/2024
 */


sealed class PullRequestState {
    data class ShowPullRequests(val list: List<PullRequest>) : PullRequestState()
    data object ShowLoading : PullRequestState()
    data object ShowError : PullRequestState()
    data object HideLoading : PullRequestState()
    data object ShowEmptyPullRequests : PullRequestState()
}
