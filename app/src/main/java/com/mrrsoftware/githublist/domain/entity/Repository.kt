package com.mrrsoftware.githublist.domain.entity

/**
 * Created by Welbert on 25/07/2024
 */
data class Repository(
    val id : String,
    val title : String,
    val description : String,
    val pullRequestsCount : Int,
    val starsCount : Int,
    val user : User
)
