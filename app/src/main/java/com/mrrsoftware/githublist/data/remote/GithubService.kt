package com.mrrsoftware.githublist.data.remote

import com.mrrsoftware.githublist.data.model.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Welbert on 25/07/2024
 */
interface GithubService {

    @GET("search/repositories?q=language:Java&sort=stars")
    suspend fun fetchRepositories(
        @Query("page") indexStart: Int,
    ): Response<RepositoryResponse>
}