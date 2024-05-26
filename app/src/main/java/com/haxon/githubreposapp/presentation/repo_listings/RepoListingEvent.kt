package com.haxon.githubreposapp.presentation.repo_listings

import com.haxon.githubreposapp.domain.model.RepoListing

sealed interface RepoListingEvent {
    data class OnSearchQueryChanged(val query: String) : RepoListingEvent
    data class CacheData(val data: List<RepoListing>) : RepoListingEvent
}