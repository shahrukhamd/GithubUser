/*
 * Copyright (c) 2023 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.di.module

import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.base.AppDatabase
import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.data.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A separate module just for the [SearchRepository] so this can be used for injecting fake one in test(s)
 */
@Module
@InstallIn(SingletonComponent::class)
object SearchRepositoryModule {
    @Provides
    @Singleton
    fun provideSearchRepository(
        @AppModule.QGithubService service: GithubService,
        @AppModule.QAppDatabase database: AppDatabase
    ): SearchRepository {
        return SearchRepositoryImpl(service, database)
    }
}