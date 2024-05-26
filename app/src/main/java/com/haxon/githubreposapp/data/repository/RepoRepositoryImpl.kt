package com.haxon.githubreposapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.haxon.githubreposapp.data.local.RepoDatabase
import com.haxon.githubreposapp.data.mapper.toRepoEntity
import com.haxon.githubreposapp.data.mapper.toRepoListing
import com.haxon.githubreposapp.data.remote.GithubApi
import com.haxon.githubreposapp.domain.model.GitHubRepositoryItem
import com.haxon.githubreposapp.domain.model.RepoListing
import com.haxon.githubreposapp.domain.repository.RepoRepository
import com.haxon.githubreposapp.util.GeneralResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val db: RepoDatabase,
) : RepoRepository {

    private val dao = db.dao
    override suspend fun insert15Repos(repos: List<RepoListing>): Flow<GeneralResponse<Unit>> {
        return flow {
            emit(GeneralResponse.Loading(true))
            dao.clearRepoListing()
            dao.insertRepoListing(repos.map { it.toRepoEntity() })
            emit(GeneralResponse.Success(Unit))
            emit(GeneralResponse.Loading(false))
        }
    }

    override suspend fun get15ReposFromCache(): Flow<GeneralResponse<List<RepoListing>>> {
        return flow {
            emit(GeneralResponse.Loading(true))
            val localListing = dao.getRepoListing()
            emit(GeneralResponse.Success(
                data = localListing.map { it.toRepoListing() }
            ))
        }
    }

    override suspend fun getReposFromNetwork(
        query: String,
    ): Flow<PagingData<GitHubRepositoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepoPagingSource(api, query)
            }
        ).flow
    }
}