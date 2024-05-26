package com.haxon.githubreposapp.domain.repository

import com.haxon.githubreposapp.domain.model.Contributor
import com.haxon.githubreposapp.util.GeneralResponse
import kotlinx.coroutines.flow.Flow

interface ContributorRepository {
    suspend fun getContributors(url: String): Flow<GeneralResponse<List<Contributor>>>
}