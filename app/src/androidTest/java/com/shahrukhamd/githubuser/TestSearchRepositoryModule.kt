/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser

import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.di.module.SearchRepositoryModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchRepositoryModule::class]
)
abstract class TestSearchRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepo(fakeRepo: FakeSearchRepository): SearchRepository
}