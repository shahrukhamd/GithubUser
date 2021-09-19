/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser

import androidx.paging.PagingData
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.repository.SearchRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

/**
 * Implementation of a static data repository for easier testing of the UI
 */
class FakeSearchRepository @Inject constructor(): SearchRepository {

    private var userDataForTest = SampleUserDetailsObj

    override fun getPaginatedUser(query: String): Flow<PagingData<GithubUser>> {
        // todo not used in current tests
        return emptyFlow()
    }

    override suspend fun getPagingStarredUsers(): Flow<PagingData<GithubUser>> {
        // todo not used in current tests
        return emptyFlow()
    }

    override suspend fun getUserDetailsAndUpdateDb(username: String): GithubUser? {
        return userDataForTest
    }

    override suspend fun updateUser(user: GithubUser) {
        userDataForTest = user
    }
}