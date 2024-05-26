package com.haxon.githubreposapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepoEntity::class],
    version = 1
)
abstract class RepoDatabase: RoomDatabase() {
    abstract val dao: RepoDao
}