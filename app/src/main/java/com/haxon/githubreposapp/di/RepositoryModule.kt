package com.haxon.githubreposapp.di

import com.haxon.githubreposapp.data.repository.ContributorRepositoryImpl
import com.haxon.githubreposapp.data.repository.RepoRepositoryImpl
import com.haxon.githubreposapp.domain.repository.ContributorRepository
import com.haxon.githubreposapp.domain.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepoRepository(
        repoRepositoryImpl: RepoRepositoryImpl
    ): RepoRepository

    @Binds
    @Singleton
    abstract fun bindContributorRepository(
        contributorRepositoryImpl: ContributorRepositoryImpl
    ): ContributorRepository

}