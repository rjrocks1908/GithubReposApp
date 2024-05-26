package com.haxon.githubreposapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haxon.githubreposapp.data.remote.GithubApi
import com.haxon.githubreposapp.domain.model.GitHubRepositoryItem
import retrofit2.HttpException
import java.io.IOException

class RepoPagingSource(
    private val api: GithubApi,
    private val query: String
) : PagingSource<Int, GitHubRepositoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, GitHubRepositoryItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubRepositoryItem> {
        val page = params.key ?: 1
        return try {
            val response = api.getRepositories(query, page, params.loadSize)
            LoadResult.Page(
                data = response.items,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.items.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}