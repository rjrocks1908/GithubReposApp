package com.haxon.githubreposapp.domain.model

data class RepoListing(
    val id: Int?,
    val repoName: String,
    val ownerName: String,
    val projectLink: String?,
    val ownerImageLink: String?,
    val description: String?,
    val contributorsURL: String?,
)
