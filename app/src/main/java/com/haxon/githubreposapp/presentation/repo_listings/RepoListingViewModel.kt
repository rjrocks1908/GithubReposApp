package com.haxon.githubreposapp.presentation.repo_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.haxon.githubreposapp.data.mapper.toGitHubRepositoryItem
import com.haxon.githubreposapp.data.mapper.toPagingData
import com.haxon.githubreposapp.domain.model.GitHubRepositoryItem
import com.haxon.githubreposapp.domain.repository.RepoRepository
import com.haxon.githubreposapp.util.GeneralResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListingViewModel @Inject constructor(
    private val repoRepository: RepoRepository
) : ViewModel() {

    var state by mutableStateOf(RepoListingState())
    private val _repoResponse: MutableStateFlow<PagingData<GitHubRepositoryItem>> =
        MutableStateFlow(PagingData.empty())
    var repoResponse = _repoResponse.asStateFlow()
        private set
    private var searchJob: Job? = null

    fun onEvent(event: RepoListingEvent) {
        when (event) {
            is RepoListingEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.query)
                if (event.query.isNotBlank()) {
                    state = state.copy(isLoading = true)
                    getRepoListings(event.query)
                }
            }

            is RepoListingEvent.CacheData -> {
                viewModelScope.launch {
                    repoRepository.insert15Repos(event.data)
                }
            }

            is RepoListingEvent.ShowCachedData -> {
                getReposFromCache()
            }
        }
    }

    private fun getRepoListings(
        query: String
    ) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            repoRepository.getReposFromNetwork(query)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _repoResponse.value = pagingData
                    state = state.copy(isLoading = false)
                }

        }
    }

    private fun getReposFromCache() {
        viewModelScope.launch {
            repoRepository.get15ReposFromCache().collectLatest {
                when (it) {
                    is GeneralResponse.Error -> {}
                    is GeneralResponse.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }

                    is GeneralResponse.Success -> {
                        if (!it.data.isNullOrEmpty()) {
                            it.data.toPagingData().collect { pagingData ->
                                _repoResponse.value =
                                    pagingData.map { data -> data.toGitHubRepositoryItem() }
                            }
                        }
                    }
                }
            }
        }
    }

}