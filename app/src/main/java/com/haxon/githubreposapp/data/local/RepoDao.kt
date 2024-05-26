package com.haxon.githubreposapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepoListing(repoListing: List<RepoEntity>)

    @Query("DELETE FROM RepoEntity")
    suspend fun clearRepoListing()

    @Query("SELECT * FROM RepoEntity")
    suspend fun getRepoListing(): List<RepoEntity>

}