package com.haxon.githubreposapp.domain.repository

import androidx.paging.PagingData
import com.haxon.githubreposapp.domain.model.GitHubRepositoryItem
import com.haxon.githubreposapp.domain.model.RepoListing
import com.haxon.githubreposapp.util.GeneralResponse
import kotlinx.coroutines.flow.Flow


interface RepoRepository {

    suspend fun insert15Repos(repos: List<RepoListing>): Flow<GeneralResponse<Unit>>

    suspend fun get15ReposFromCache(): Flow<GeneralResponse<List<RepoListing>>>

    suspend fun getReposFromNetwork(
        query: String,
    ): Flow<PagingData<GitHubRepositoryItem>>
}