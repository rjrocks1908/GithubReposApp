package com.haxon.githubreposapp.data.remote

import com.haxon.githubreposapp.domain.model.GitHubApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GitHubApiResponse

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

}