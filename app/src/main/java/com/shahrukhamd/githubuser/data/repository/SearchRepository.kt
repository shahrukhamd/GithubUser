/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.source.GithubPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val NETWORK_PAGE_SIZE = 30

class MainRepository @Inject constructor(private val service: GithubService) {

    fun getPaginatedUser(query: String): Flow<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }

    suspend fun getUserDetails(username: String): GithubUser? {
        // todo refactor this for more error handling
        return service.getUserDetails(username).body()
    }
}