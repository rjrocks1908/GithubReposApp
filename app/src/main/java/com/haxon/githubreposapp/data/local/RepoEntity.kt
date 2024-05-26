package com.haxon.githubreposapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoEntity(
    val repoName: String,
    val ownerName: String,
    val projectLink: String,
    val ownerImageLink: String,
    val description: String,
    val contributorsURL: String,
    @PrimaryKey val id: Int? = null,
)
