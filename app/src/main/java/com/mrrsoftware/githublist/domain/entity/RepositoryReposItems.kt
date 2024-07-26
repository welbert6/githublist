package com.mrrsoftware.githublist.domain.entity

/**
 * Created by Welbert on 25/07/2024
 */
data class RepositoryReposItems(
    val hasMore : Boolean,
    val list: List<Repository>
)