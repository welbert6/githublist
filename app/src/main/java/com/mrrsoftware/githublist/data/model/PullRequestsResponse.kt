package com.mrrsoftware.githublist.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Welbert on 26/07/2024
 */
data class PullRequestsResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("body")
    val body  : String? = "",
    @SerializedName("user")
    val user : OwnerResponse,
)