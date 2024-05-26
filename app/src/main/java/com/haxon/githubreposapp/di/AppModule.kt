package com.haxon.githubreposapp.di

import android.app.Application
import androidx.room.Room
import com.haxon.githubreposapp.data.local.RepoDatabase
import com.haxon.githubreposapp.data.remote.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideRepoDatabase(app: Application): RepoDatabase {
        return Room.databaseBuilder(
            app,
            RepoDatabase::class.java,
            "repos_db"
        ).build()
    }

}