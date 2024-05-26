package com.haxon.githubreposapp.domain.model

data class Contributor(
    val login: String,
    val avatar_url: String,
    val contributions: Int
)
