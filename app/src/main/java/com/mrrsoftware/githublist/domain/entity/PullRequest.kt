package com.mrrsoftware.githublist.domain.entity

/**
 * Created by Welbert on 26/07/2024
 */
data class PullRequest(
    val id : Int,
    val title : String,
    val body  : String,
    val user : User
)