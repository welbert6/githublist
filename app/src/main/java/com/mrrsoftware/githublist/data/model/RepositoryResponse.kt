package com.mrrsoftware.githublist.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Welbert on 25/07/2024
 */
data class RepositoryResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items : List<RepositoryItemResponse>
)


data class RepositoryItemResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("owner")
    val owner : OwnerResponse,
    @SerializedName("open_issues_count")
    val pullRequestsCount : Int = 0,
    @SerializedName("stargazers_count")
    val starCount : Int = 0
)

data class OwnerResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("login")
    val username : String,
    @SerializedName("avatar_url")
    val avatarUrl: String

)