package com.haxon.githubreposapp.data.repository

import com.haxon.githubreposapp.data.remote.ContributorApi
import com.haxon.githubreposapp.domain.model.Contributor
import com.haxon.githubreposapp.domain.repository.ContributorRepository
import com.haxon.githubreposapp.util.GeneralResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContributorRepositoryImpl @Inject constructor(
    private val api: ContributorApi
) : ContributorRepository {
    override suspend fun getContributors(url: String): Flow<GeneralResponse<List<Contributor>>> {
        return flow {
            emit(GeneralResponse.Loading())
            val contributors = try {
                api.getContributors(url)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(GeneralResponse.Error(message = e.localizedMessage ?: "Unknown error"))
                null
            }
            contributors?.let {
                emit(GeneralResponse.Success(
                    data = it
                ))
            }
            emit(GeneralResponse.Loading(isLoading = false))
        }
    }
}