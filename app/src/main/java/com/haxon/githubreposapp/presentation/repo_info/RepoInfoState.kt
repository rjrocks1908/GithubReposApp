package com.haxon.githubreposapp.presentation.repo_info

import com.haxon.githubreposapp.domain.model.Contributor

data class RepoInfoState(
    val contributors: List<Contributor> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
