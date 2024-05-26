package com.haxon.githubreposapp.data.mapper

import com.haxon.githubreposapp.data.local.RepoEntity
import com.haxon.githubreposapp.domain.model.GitHubRepositoryItem
import com.haxon.githubreposapp.domain.model.RepoListing

fun RepoEntity.toRepoListing(): RepoListing {
    return RepoListing(
        id = id,
        repoName = repoName,
        ownerName = ownerName,
        projectLink = projectLink,
        ownerImageLink = ownerImageLink,
        description = description,
        contributorsURL = contributorsURL
    )
}

fun RepoListing.toRepoEntity(): RepoEntity {
    return RepoEntity(
        repoName = repoName,
        ownerName = ownerName,
        projectLink = projectLink ?: "",
        ownerImageLink = ownerImageLink ?: "",
        description = description ?: "",
        contributorsURL = contributorsURL ?: "",
    )
}

fun GitHubRepositoryItem.toRepoListing(): RepoListing {
    return RepoListing(
        id = id,
        repoName = name,
        ownerName = owner.login,
        projectLink = projectUrl,
        ownerImageLink = owner.avatarUrl,
        description = description,
        contributorsURL = contributorsUrl
    )
}