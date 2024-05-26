package com.haxon.githubreposapp.data.mapper

import androidx.paging.PagingData
import com.haxon.githubreposapp.data.local.RepoEntity
import com.haxon.githubreposapp.domain.model.GitHubRepositoryItem
import com.haxon.githubreposapp.domain.model.Owner
import com.haxon.githubreposapp.domain.model.RepoListing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
        projectLink = html_url,
        ownerImageLink = owner.avatar_url,
        description = description,
        contributorsURL = contributors_url
    )
}

fun RepoListing.toGitHubRepositoryItem(): GitHubRepositoryItem {
    return GitHubRepositoryItem(
        name = repoName,
        owner = Owner(login = ownerName, avatar_url = ownerImageLink ?: ""),
        id = id ?: 0,
        description = description ?: "",
        html_url = projectLink ?: "",
        contributors_url = contributorsURL ?: ""

    )
}

fun <T : Any> List<T>.toPagingData(): Flow<PagingData<T>> {
    return flowOf(PagingData.from(this))
}