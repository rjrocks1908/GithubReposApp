package com.haxon.githubreposapp.presentation.repo_listings

import com.haxon.githubreposapp.domain.model.RepoListing

data class RepoListingState(
    val repos: List<RepoListing> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
