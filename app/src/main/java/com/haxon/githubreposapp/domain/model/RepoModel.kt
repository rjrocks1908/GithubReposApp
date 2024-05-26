package com.haxon.githubreposapp.domain.model

import com.squareup.moshi.Json

data class GitHubApiResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    val items: List<GitHubRepositoryItem>
)

data class GitHubRepositoryItem(
    val name: String,
    val owner: Owner,
    val id: Int,
    val description: String,
    @Json(name = "html_url") val projectUrl: String,
    @Json(name = "contributors_url") val contributorsUrl: String

)

data class Owner(
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)